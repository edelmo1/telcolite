package ba.edi.telcolite.customer;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCustomerRequest(
        @NotBlank @Size(max=50) String firstName,
        @NotBlank @Size(max=50) String lastName,
        @NotBlank @Size(max=50) @Email String email,
        @NotBlank @Size(min=8, max=100) String password) {
}
