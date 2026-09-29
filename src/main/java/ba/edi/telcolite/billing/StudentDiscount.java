package ba.edi.telcolite.billing;

public class StudentDiscount implements DiscountPolicy{

    @Override
    public double apply(double amount){
        return amount-amount*0.2;
    }
}
