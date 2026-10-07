package ba.edi.telcolite.customer;

import ba.edi.telcolite.common.ConflictException;

public class DuplicateEmailException extends ConflictException {
    public DuplicateEmailException(String email) {

        super("Duplicated email: " + email);
    }
}
