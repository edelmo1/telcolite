package ba.edi.telcolite.tariff;

public record TariffPlan(String code,
                         String name,
                         double monthlyPrice,
                         int includedMinutes,
                         int includedSms,
                         int includedGb) {

    public TariffPlan {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Tariff code must not be blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Tariff name must not be blank");
        }
        if (monthlyPrice < 0) {
            throw new IllegalArgumentException("Monthly price must not be negative");
        }
        if (includedMinutes < 0 || includedSms < 0 || includedGb < 0) {
            throw new IllegalArgumentException("Included amounts must not be negative");
        }
    }

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