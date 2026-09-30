package ba.edi.telcolite.customer;

import ba.edi.telcolite.notification.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final NotificationService notificationService;

    public CustomerService(CustomerRepository customerRepository,
                           NotificationService notificationService) {
        this.customerRepository = customerRepository;
        this.notificationService = notificationService;
    }

    public CustomerResponse create(CreateCustomerRequest request) {
        if (customerRepository.existByEmail(request.email())) {
            throw new DuplicateEmailException(request.email());
        }

        Customer customer = new Customer(null,request.firstName(), request.lastName(), request.email());
        Customer saved = customerRepository.save(customer);

        notificationService.broadcast(saved.getEmail(),
                "Dobrodošli u TelcoLite, " + saved.getFirstName() + "!");

        return CustomerResponse.from(saved);
    }

    public List<CustomerResponse> findAll() {
        return customerRepository.findAll().stream()
                .map(CustomerResponse::from)
                .toList();
    }

    public CustomerResponse getById(Long id) {
        return CustomerResponse.from(getCustomer(id));
    }

    public CustomerResponse block(Long id) {
        Customer customer = getCustomer(id);
        customer.block();
        return CustomerResponse.from(customerRepository.save(customer));
    }

    public CustomerResponse unblock(Long id) {
        Customer customer = getCustomer(id);
        customer.unblock();
        return CustomerResponse.from(customerRepository.save(customer));
    }

    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id.toString()));
    }
}