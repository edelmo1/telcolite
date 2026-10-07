package ba.edi.telcolite.subscription;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateSubscriptionRequest(
        @NotBlank @Size(max=50) String tariffCode,
        @NotBlank @Size(max=50) @Pattern(regexp = "06\\d{7,8}", message = "must start with 06 and have 9 or 10 digits") String phoneNumber) {
}
