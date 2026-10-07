package ba.edi.telcolite.subscription;

import ba.edi.telcolite.tariff.Tariff;

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
                                            double monthlyPrice,
                                            String currency) {
        Tariff tariff = subscription.getTariff();
        return new SubscriptionResponse(
                subscription.getId(),
                subscription.getCustomer().getId(),
                subscription.getPhoneNumber(),
                tariff.getCode(),
                tariff.getName(),
                monthlyPrice,
                currency,
                subscription.getStatus(),
                subscription.getActivatedAt());
    }
}
