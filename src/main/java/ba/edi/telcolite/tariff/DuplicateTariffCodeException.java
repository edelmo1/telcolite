package ba.edi.telcolite.tariff;

import ba.edi.telcolite.common.ConflictException;

public class DuplicateTariffCodeException extends ConflictException {
    public DuplicateTariffCodeException(String message) {

        super("Tariff code already exists: " + message);
    }
}
