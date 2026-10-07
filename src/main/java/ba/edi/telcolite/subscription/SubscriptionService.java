package ba.edi.telcolite.subscription;

import ba.edi.telcolite.billing.PriceCalculator;
import ba.edi.telcolite.customer.Customer;
import ba.edi.telcolite.customer.CustomerService;
import ba.edi.telcolite.notification.NotificationService;
import ba.edi.telcolite.tariff.Tariff;
import ba.edi.telcolite.tariff.TariffService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SubscriptionService {

    private static final int MAX_ACTIVE_SUBSCRIPTIONS = 3;

    private final SubscriptionRepository subscriptionRepository;
    private final CustomerService customerService;
    private final TariffService tariffService;
    private final PriceCalculator priceCalculator;
    private final NotificationService notificationService;
    private final String currency;

    public SubscriptionService(SubscriptionRepository subscriptionRepository,
                               CustomerService customerService,
                               TariffService tariffService,
                               PriceCalculator priceCalculator,
                               NotificationService notificationService,
                               @Value("${telcolite.currency}") String currency) {
        this.subscriptionRepository = subscriptionRepository;
        this.customerService = customerService;
        this.tariffService = tariffService;
        this.priceCalculator = priceCalculator;
        this.notificationService = notificationService;
        this.currency = currency;
    }

    @Transactional
    public SubscriptionResponse create(Long customerId, CreateSubscriptionRequest request) {
        Customer customer = customerService.getCustomer(customerId);
        if (!customer.isActive()) {
            throw new CustomerNotActiveException(customerId);
        }

        Tariff tariff = tariffService.getTariff(request.tariffCode());

        if (subscriptionRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new DuplicatePhoneNumberException(request.phoneNumber());
        }

        long activeCount = subscriptionRepository.countByCustomerIdAndStatus(customerId, SubscriptionStatus.ACTIVE);
        if (activeCount >= MAX_ACTIVE_SUBSCRIPTIONS) {
            throw new SubscriptionLimitExceededException(customerId, MAX_ACTIVE_SUBSCRIPTIONS);
        }

        Subscription subscription = new Subscription(customer, tariff, request.phoneNumber());
        Subscription saved = subscriptionRepository.save(subscription);

        notificationService.broadcast(customer.getEmail(),
                "Aktiviran je broj %s na tarifi %s.".formatted(saved.getPhoneNumber(), tariff.getName()));

        return toResponse(saved);
    }

    public List<SubscriptionResponse> getAllByCustomerId(Long customerId) {
        customerService.getCustomer(customerId);

        return subscriptionRepository.findAllByCustomerId(customerId).stream()
                .map(this::toResponse)
                .toList();
    }

    public SubscriptionResponse getById(Long id) {
        return toResponse(getSubscription(id));
    }

    @Transactional
    public SubscriptionResponse suspend(Long id) {
        Subscription subscription = getSubscription(id);
        subscription.suspend();
        return toResponse(subscriptionRepository.save(subscription));
    }

    @Transactional
    public SubscriptionResponse resume(Long id) {
        Subscription subscription = getSubscription(id);
        subscription.resume();
        return toResponse(subscriptionRepository.save(subscription));
    }

    @Transactional
    public SubscriptionResponse terminate(Long id) {
        Subscription subscription = getSubscription(id);
        subscription.terminate();
        return toResponse(subscriptionRepository.save(subscription));
    }

    public Subscription getSubscription(Long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new SubscriptionNotFoundException(id));
    }

    private SubscriptionResponse toResponse(Subscription subscription) {
        double price = priceCalculator.finalMonthlyPrice(subscription.getTariff());
        return SubscriptionResponse.from(subscription, price, currency);
    }
}