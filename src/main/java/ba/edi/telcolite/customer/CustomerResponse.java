package ba.edi.telcolite.customer;

import java.time.LocalDateTime;

public record CustomerResponse(Long id,
                               String fullName,
                               String email,
                               CustomerStatus status,
                               LocalDateTime createdAt) {

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName() + " " + customer.getLastName(),
                customer.getEmail(),
                customer.getStatus(),
                customer.getCreatedAt());
    }
}