package ba.edi.telcolite.customer;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {

        super("Duplicated email: " + message);
    }
}
