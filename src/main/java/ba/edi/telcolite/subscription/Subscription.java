package ba.edi.telcolite.subscription;

import ba.edi.telcolite.customer.Customer;
import ba.edi.telcolite.tariff.Tariff;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tariff_code")
    private Tariff tariff;

    @Column(nullable = false, unique = true)
    private  String phoneNumber;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    @Column(nullable = false)
    private  LocalDateTime activatedAt;

    protected Subscription(){}

    public Subscription(Customer customer,
                        Tariff tariff,
                        String phoneNumber) {

        this.customer = customer;
        this.tariff = tariff;
        this.phoneNumber = phoneNumber;
        this.status = SubscriptionStatus.ACTIVE;
        this.activatedAt = LocalDateTime.now();
    }

    public void suspend(){
        if(this.status==SubscriptionStatus.SUSPENDED){
            throw new IllegalStateException("Already suspended");
        }
        else if(this.status==SubscriptionStatus.TERMINATED){
            throw new IllegalStateException("Terminated subscription can't be suspended");
        }
        this.status=SubscriptionStatus.SUSPENDED;
    }

    public void resume(){
        if(this.status==SubscriptionStatus.ACTIVE){
            throw new IllegalStateException("Already activated");
        }
        else if(this.status==SubscriptionStatus.TERMINATED){
            throw new IllegalStateException("Terminated subscription can't be resumed");
        }
        this.status=SubscriptionStatus.ACTIVE;
    }

    public void terminate(){
        if(this.status==SubscriptionStatus.TERMINATED){
            throw new IllegalStateException("Already terminated");
        }
        this.status=SubscriptionStatus.TERMINATED;
    }

    public LocalDateTime getActivatedAt() {
        return activatedAt;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Long getId() {
        return id;
    }

    public boolean isActive() {
        return status == SubscriptionStatus.ACTIVE;
    }
}
