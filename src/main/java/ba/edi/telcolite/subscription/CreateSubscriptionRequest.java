package ba.edi.telcolite.subscription;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateSubscriptionRequest(
        @NotBlank @Size(max=50) String tariffCode,
        @NotBlank @Size(max=50) @Pattern(regexp = "06\\d{7,8}") String phoneNumber) {
}
