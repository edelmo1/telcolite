package ba.edi.telcolite.subscription;

import java.time.LocalDateTime;

public class Subscription {

    private Long id;
    private final Long customerId;
    private String tariffCode;
    private final String phoneNumber;
    private SubscriptionStatus status;
    private final LocalDateTime activatedAt;

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
