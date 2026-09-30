package ba.edi.telcolite.subscription;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private  Long customerId;

    @Column(nullable = false)
    private String tariffCode;

    @Column(nullable = false)
    private  String phoneNumber;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    @Column(nullable = false)
    private  LocalDateTime activatedAt;

    protected Subscription(){}

    public Subscription(Long id, Long customerId,
                        String tariffCode,
                        String phoneNumber) {

        this.id = id;
        this.customerId = customerId;
        this.tariffCode = tariffCode;
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

    public String getTariffCode() {
        return tariffCode;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }
    public boolean isActive() {
        return status == SubscriptionStatus.ACTIVE;
    }
}
