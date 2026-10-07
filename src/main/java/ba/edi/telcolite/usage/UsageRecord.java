package ba.edi.telcolite.usage;


import ba.edi.telcolite.subscription.Subscription;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usage_records")
public class UsageRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Subscription subscription;

    @Enumerated(EnumType.STRING)
    private UsageType type;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private LocalDateTime recordedAt;

    protected UsageRecord(){}

    public UsageRecord(Subscription subscription,
                       UsageType type,
                       int amount){

        this.subscription=subscription;
        this.type=type;
        this.amount=amount;
        this.recordedAt=LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public UsageType getType(){
        return type;
    }

    public int getAmount(){
        return amount;
    }

    public LocalDateTime getRecordedAt(){
        return recordedAt;
    }

}
