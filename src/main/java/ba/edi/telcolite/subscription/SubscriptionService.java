package ba.edi.telcolite.subscription;

import ba.edi.telcolite.billing.PriceCalculator;
import ba.edi.telcolite.customer.Customer;
import ba.edi.telcolite.customer.CustomerService;
import ba.edi.telcolite.notification.NotificationService;
import ba.edi.telcolite.tariff.TariffPlan;
import ba.edi.telcolite.tariff.TariffService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public SubscriptionResponse create(Long customerId, CreateSubscriptionRequest request) {
        Customer customer = customerService.getCustomer(customerId);
        if (!customer.isActive()) {
            throw new CustomerNotActiveException(customerId);
        }

        TariffPlan tariff = tariffService.getByCode(request.tariffCode());

        if (subscriptionRepository.existByPhoneNumber(request.phoneNumber())) {
            throw new DuplicatePhoneNumberException(request.phoneNumber());
        }

        long activeCount = subscriptionRepository.findAllByCustomerId(customerId).stream()
                .filter(Subscription::isActive)
                .count();
        if (activeCount >= MAX_ACTIVE_SUBSCRIPTIONS) {
            throw new SubscriptionLimitExceededException(customerId, MAX_ACTIVE_SUBSCRIPTIONS);
        }

        Subscription subscription = new Subscription(null, customerId, tariff.code(), request.phoneNumber());
        Subscription saved = subscriptionRepository.save(subscription);

        notificationService.broadcast(customer.getEmail(),
                "Aktiviran je broj %s na tarifi %s.".formatted(saved.getPhoneNumber(), tariff.name()));

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

    public SubscriptionResponse suspend(Long id) {
        Subscription subscription = getSubscription(id);
        subscription.suspend();
        return toResponse(subscriptionRepository.save(subscription));
    }

    public SubscriptionResponse resume(Long id) {
        Subscription subscription = getSubscription(id);
        subscription.resume();
        return toResponse(subscriptionRepository.save(subscription));
    }

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
        TariffPlan tariff = tariffService.getByCode(subscription.getTariffCode());
        double price = priceCalculator.finalMonthlyPrice(tariff);
        return SubscriptionResponse.from(subscription, tariff, price, currency);
    }
}