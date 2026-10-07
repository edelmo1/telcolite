package ba.edi.telcolite.usage;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RecordUsageRequest(
        @NotNull UsageType type,
        @Positive int amount) {

}
