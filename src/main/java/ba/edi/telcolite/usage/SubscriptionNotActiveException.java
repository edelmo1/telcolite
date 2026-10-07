package ba.edi.telcolite.usage;


import ba.edi.telcolite.common.ConflictException;

public class SubscriptionNotActiveException extends ConflictException {
    public SubscriptionNotActiveException(Long subscriptionId) {

        super("Subscription %d is not active, usage cannot be recorded".formatted(subscriptionId));
    }
}
