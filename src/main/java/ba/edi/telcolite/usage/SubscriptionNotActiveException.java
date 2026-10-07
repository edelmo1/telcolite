package ba.edi.telcolite.usage;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SubscriptionNotActiveException extends RuntimeException {
    public SubscriptionNotActiveException(Long subscriptionId) {

        super("Subscription %d is not active, usage cannot be recorded".formatted(subscriptionId));
    }
}
