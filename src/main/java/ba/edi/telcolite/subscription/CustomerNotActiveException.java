package ba.edi.telcolite.subscription;

import ba.edi.telcolite.common.NotFoundException;

public class CustomerNotActiveException extends NotFoundException {
    public CustomerNotActiveException(Long customerId) {
        super("Customer %d is not active and cannot get new subscriptions".formatted(customerId));
    }
}
