package ba.edi.telcolite.subscription;

import ba.edi.telcolite.common.NotFoundException;

public class SubscriptionNotFoundException extends NotFoundException {

    public SubscriptionNotFoundException(Long id) {
        super("Subscription not found: " + id);
    }

    public SubscriptionNotFoundException(String phoneNumber) {
        super("Subscription not found for phone number: " + phoneNumber);
    }
}