package ba.edi.telcolite.billing;

public interface DiscountPolicy {

    double apply(double amount);
}
