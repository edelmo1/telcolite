package ba.edi.telcolite.tariff;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tariffs")
public class Tariff {

    @Id
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double monthlyPrice;

    @Column(nullable = false)
    private int includedMinutes;

    @Column(nullable = false)
    private int includedSms;

    @Column(nullable = false)
    private int includedGb;

    protected Tariff(){}

    public Tariff(String code, String name, double monthlyPrice,
                  int includedMinutes, int includedSms, int includedGb) {
        this.code = code.toUpperCase();
        this.name = name;
        this.monthlyPrice = monthlyPrice;
        this.includedMinutes = includedMinutes;
        this.includedSms = includedSms;
        this.includedGb = includedGb;
    }

    public void update(String name, double monthlyPrice,
                       int includedMinutes, int includedSms, int includedGb) {
        this.name = name;
        this.monthlyPrice = monthlyPrice;
        this.includedMinutes = includedMinutes;
        this.includedSms = includedSms;
        this.includedGb = includedGb;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public double getMonthlyPrice() { return monthlyPrice; }
    public int getIncludedMinutes() { return includedMinutes; }
    public int getIncludedSms() { return includedSms; }
    public int getIncludedGb() { return includedGb; }
}
