package ba.edi.telcolite.tariff;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateTariffCodeException extends RuntimeException {
    public DuplicateTariffCodeException(String message) {

        super("Tariff code already exists: " + message);
    }
}
