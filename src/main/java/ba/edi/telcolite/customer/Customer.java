package ba.edi.telcolite.customer;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private  String firstName;

    @Column(nullable=false)
    private  String lastName;

    @Column(nullable=false, unique=true)
    private  String email;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    @Column(nullable=false)
    private  LocalDateTime createdAt;

    protected Customer() {
    }

    public Customer(Long id, String firstName,
                    String lastName,
                    String email){

        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.status=CustomerStatus.ACTIVE;
        this.createdAt=LocalDateTime.now();
    }

    public void block(){
        if(status==CustomerStatus.BLOCKED){
            throw new IllegalStateException("Customer is already blocked");
        }
        status=CustomerStatus.BLOCKED;
    }

    public void unblock(){
        if(status==CustomerStatus.ACTIVE){
            throw new IllegalStateException("Customer is already active");
        }
        status=CustomerStatus.ACTIVE;
    }

    public boolean isActive(){
        return status==CustomerStatus.ACTIVE;
    }
    public void setId(Long id) { this.id = id; }
    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public CustomerStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

}
