package ba.edi.telcolite.subscription;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SubscriptionLimitExceededException extends RuntimeException {

    public SubscriptionLimitExceededException(Long customerId, int limit) {
        super("Customer %d already has the maximum of %d active subscriptions"
                .formatted(customerId, limit));
    }
}