package ba.edi.telcolite.subscription;

import ba.edi.telcolite.common.ConflictException;

public class CustomerNotActiveException extends ConflictException {
    public CustomerNotActiveException(Long customerId) {
        super("Customer %d is not active and cannot get new subscriptions".formatted(customerId));
    }
}
