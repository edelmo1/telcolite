package ba.edi.telcolite.customer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CustomerRepository {

    private final Map<Long, Customer> customers = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public Customer save(Customer customer){
        if(customer.getId()==null){
            customer.setId(idGenerator.incrementAndGet());
        }
        customers.put(customer.getId(),customer);
        return customer;
    }

    public Optional<Customer> findById(Long id){
        return Optional.ofNullable(customers.get(id));
    }

    public List<Customer> findAll(){
        return customers.values().stream().toList();
    }

    public boolean existByEmail(String email){
        return customers.values().stream().anyMatch(c->c.getEmail().equals(email));
    }

}
