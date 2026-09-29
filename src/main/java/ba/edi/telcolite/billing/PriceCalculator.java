package ba.edi.telcolite.billing;


import ba.edi.telcolite.tariff.TariffPlan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PriceCalculator {

    private final DiscountPolicy discount;
    private final double vatRate;

    public PriceCalculator(DiscountPolicy discount,
                           @Value("${telcolite.vat-rate}") double vatRate){

        this.discount=discount;
        this.vatRate=vatRate;
    }

    public double finalMonthlyPrice(TariffPlan plan){

        double discountApplied = discount.apply(plan.monthlyPrice());
        return discountApplied*(1+vatRate);
    }
}
