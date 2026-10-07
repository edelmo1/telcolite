package ba.edi.telcolite.subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>  {

    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByTariffCode(String tariffCode);
    List<Subscription> findAllByCustomerId(Long customerId);
    long countByCustomerIdAndStatus(Long customerId, SubscriptionStatus status);
    Optional<Subscription> findByPhoneNumber(String phoneNumber);

    @Query("""
        select s from Subscription s
        join fetch s.tariff
        where s.customer.id = :customerId
        order by s.id
        """)
    List<Subscription> findAllByCustomerIdWithTariff(Long customerId);
}
