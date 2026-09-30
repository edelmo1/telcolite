package ba.edi.telcolite.subscription;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CustomerNotActiveException extends RuntimeException {
    public CustomerNotActiveException(Long customerId) {
        super("Customer %d is not active and cannot get new subscriptions".formatted(customerId));
    }
}
