package ba.edi.telcolite.tariff;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record TariffPlan(
        @NotBlank @Size(max=50) String code,
        @NotBlank @Size(max=50) String name,
        @PositiveOrZero double monthlyPrice,
        @PositiveOrZero int includedMinutes,
        @PositiveOrZero int includedSms,
        @PositiveOrZero int includedGb) {

    public static TariffPlan from(Tariff tariff) {
        return new TariffPlan(
                tariff.getCode(),
                tariff.getName(),
                tariff.getMonthlyPrice(),
                tariff.getIncludedMinutes(),
                tariff.getIncludedSms(),
                tariff.getIncludedGb());
    }

    public Tariff toEntity() {
        return new Tariff(code, name, monthlyPrice, includedMinutes, includedSms, includedGb);
    }
}