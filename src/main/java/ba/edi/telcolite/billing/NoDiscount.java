package ba.edi.telcolite.billing;

public class NoDiscount implements DiscountPolicy{

    @Override
    public double apply(double amount){
        return amount;
    }
}
