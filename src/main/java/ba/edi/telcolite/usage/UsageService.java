package ba.edi.telcolite.usage;

import ba.edi.telcolite.subscription.Subscription;
import ba.edi.telcolite.subscription.SubscriptionService;
import ba.edi.telcolite.tariff.Tariff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;

@Service
@Transactional(readOnly = true)
public class UsageService {

    private static final int MB_PER_GB = 1024;

    private final UsageRecordRepository usageRecordRepository;
    private final SubscriptionService subscriptionService;

    public UsageService(UsageRecordRepository usageRecordRepository,
                        SubscriptionService subscriptionService) {
        this.usageRecordRepository = usageRecordRepository;
        this.subscriptionService = subscriptionService;
    }

    @Transactional
    public UsageRecordResponse record(Long subscriptionId, RecordUsageRequest request) {
        Subscription subscription = subscriptionService.getSubscription(subscriptionId);
        if (!subscription.isActive()) {
            throw new SubscriptionNotActiveException(subscriptionId);
        }

        UsageRecord record = new UsageRecord(subscription, request.type(), request.amount());
        return UsageRecordResponse.from(usageRecordRepository.save(record));
    }

    public UsageSummaryResponse summary(Long subscriptionId, YearMonth month) {
        Subscription subscription = subscriptionService.getSubscription(subscriptionId);
        Tariff tariff = subscription.getTariff();

        LocalDateTime from = month.atDay(1).atStartOfDay();
        LocalDateTime to = month.plusMonths(1).atDay(1).atStartOfDay();

        long usedMinutes = usageRecordRepository.sumAmount(subscriptionId, UsageType.CALL, from, to);
        long usedSms = usageRecordRepository.sumAmount(subscriptionId, UsageType.SMS, from, to);
        long usedMb = usageRecordRepository.sumAmount(subscriptionId, UsageType.DATA, from, to);

        return new UsageSummaryResponse(
                subscription.getId(),
                subscription.getPhoneNumber(),
                tariff.getCode(),
                month,
                UsageItem.of(usedMinutes, tariff.getIncludedMinutes(), "min"),
                UsageItem.of(usedSms, tariff.getIncludedSms(), "sms"),
                UsageItem.of(usedMb, (long) tariff.getIncludedGb() * MB_PER_GB, "MB"));
    }
}