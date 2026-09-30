package ba.edi.telcolite.subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>  {

    boolean existsByPhoneNumber(String phoneNumber);

    List<Subscription> findAllByCustomerId(Long customerId);
}
