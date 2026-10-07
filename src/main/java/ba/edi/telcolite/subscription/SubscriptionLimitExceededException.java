package ba.edi.telcolite.subscription;

import ba.edi.telcolite.common.ConflictException;

public class SubscriptionLimitExceededException extends ConflictException {

    public SubscriptionLimitExceededException(Long customerId, int limit) {
        super("Customer %d already has the maximum of %d active subscriptions"
                .formatted(customerId, limit));
    }
}