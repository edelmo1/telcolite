package ba.edi.telcolite.subscription;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class SubscriptionRepository {

    private final Map<Long, Subscription> subscriptions = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public Subscription save (Subscription subscription){
        if(subscription.getId()==null){
            subscription.setId(idGenerator.incrementAndGet());
        }
        subscriptions.put(subscription.getId(), subscription);
        return subscription;
    }

    public Optional<Subscription> findById(Long id){
        return Optional.ofNullable(subscriptions.get(id));
    }

    public List<Subscription> findAll(){
        return subscriptions.values().stream().toList();
    }

    public boolean existByPhoneNumber(String phoneNumber){
        return subscriptions.values().stream().anyMatch(s->s.getPhoneNumber().equals(phoneNumber));
    }

    public List<Subscription> findAllByCustomerId(Long customerId){
        return subscriptions.values().stream().filter(s-> Objects.equals(s.getCustomerId(), customerId)).toList();
    }

}
