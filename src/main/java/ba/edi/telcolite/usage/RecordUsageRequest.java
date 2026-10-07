package ba.edi.telcolite.usage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record RecordUsageRequest(
        @NotBlank UsageType type,
        @Positive int amount) {

}
