package ba.edi.telcolite.tariff;

public record TariffPriceResponse(String code, double basePrice, double finalPrice, String currency) {
}