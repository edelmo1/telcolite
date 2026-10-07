package ba.edi.telcolite.tariff;

import ba.edi.telcolite.common.NotFoundException;

public class TariffNotFoundException extends NotFoundException {
    public TariffNotFoundException(String message) {
        super("Tariff not found: " + message);
    }
}
