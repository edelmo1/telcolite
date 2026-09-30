package ba.edi.telcolite.billing;


import ba.edi.telcolite.tariff.Tariff;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PriceCalculator {

    private final DiscountPolicy discount;
    private final double vatRate;

    public PriceCalculator(DiscountPolicy discount,
                           @Value("${telcolite.vat-rate}") double vatRate) {
        if (vatRate < 0 || vatRate > 1) {
            throw new IllegalArgumentException("VAT rate must be between 0 and 1, but was " + vatRate);
        }
        this.discount = discount;
        this.vatRate = vatRate;
    }

    public double finalMonthlyPrice(Tariff plan){

        double discountApplied = discount.apply(plan.getMonthlyPrice());
        return discountApplied*(1+vatRate);
    }
}
