package ba.edi.telcolite.customer;

import ba.edi.telcolite.common.NotFoundException;

public class CustomerNotFoundException extends NotFoundException {
    public CustomerNotFoundException(Long id) {

        super("Customer not found: " + id);
    }
}
