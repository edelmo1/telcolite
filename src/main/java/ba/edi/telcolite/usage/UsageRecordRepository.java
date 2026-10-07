package ba.edi.telcolite.usage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface UsageRecordRepository extends JpaRepository<UsageRecord, Long> {

    @Query("""
            select coalesce(sum(u.amount), 0) from UsageRecord u
                                   where u.subscription.id = :subscriptionId
                                     and u.type = :type
                                     and u.recordedAt >= :from and u.recordedAt < :to
            """)
    long sumAmount(Long subscriptionId, UsageType type, LocalDateTime from, LocalDateTime to);


}
