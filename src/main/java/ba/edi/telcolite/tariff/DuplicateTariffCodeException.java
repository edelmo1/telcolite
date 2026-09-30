package ba.edi.telcolite.tariff;

public class DuplicateTariffCodeException extends RuntimeException {
    public DuplicateTariffCodeException(String message) {

        super("Tariff code already exist: " + message);
    }
}
