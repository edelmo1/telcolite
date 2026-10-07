package ba.edi.telcolite.subscription;

import ba.edi.telcolite.common.NotFoundException;

public class SubscriptionNotFoundException extends NotFoundException {
    public SubscriptionNotFoundException(Long id) {
        super("Subscription not found: " + id);
    }
}