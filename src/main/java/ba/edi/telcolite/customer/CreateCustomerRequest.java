package ba.edi.telcolite.customer;


public record CreateCustomerRequest(String firstName,
                                    String lastName,
                                    String email) {

    public CreateCustomerRequest {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name must not be blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name must not be blank");
        }
        if (email == null || !email.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
    }
}
