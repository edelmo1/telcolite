package ba.edi.telcolite.subscription;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatePhoneNumberException extends RuntimeException {
    public DuplicatePhoneNumberException(String phoneNumber) {
        super("Phone number is already in use: " + phoneNumber);
    }
}