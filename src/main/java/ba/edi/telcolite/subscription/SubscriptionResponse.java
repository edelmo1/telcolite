package ba.edi.telcolite.subscription;

import ba.edi.telcolite.tariff.Tariff;
import ba.edi.telcolite.tariff.TariffPlan;

import java.time.LocalDateTime;

public record SubscriptionResponse(Long id,
                                   Long customerId,
                                   String phoneNumber,
                                   String tariffCode,
                                   String tariffName,
                                   double monthlyPrice,
                                   String currency,
                                   SubscriptionStatus status,
                                   LocalDateTime activatedAt
                                   ) {

    public static SubscriptionResponse from(Subscription subscription,
                                            Tariff tariff,
                                            double monthlyPrice,
                                            String currency) {
        return new SubscriptionResponse(
                subscription.getId(),
                subscription.getCustomerId(),
                subscription.getPhoneNumber(),
                tariff.getCode(),
                tariff.getName(),
                monthlyPrice,
                currency,
                subscription.getStatus(),
                subscription.getActivatedAt());
    }
}
