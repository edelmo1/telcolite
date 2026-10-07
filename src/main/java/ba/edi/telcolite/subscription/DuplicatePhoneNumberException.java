package ba.edi.telcolite.subscription;

import ba.edi.telcolite.common.ConflictException;

public class DuplicatePhoneNumberException extends ConflictException {
    public DuplicatePhoneNumberException(String phoneNumber) {
        super("Phone number is already in use: " + phoneNumber);
    }
}