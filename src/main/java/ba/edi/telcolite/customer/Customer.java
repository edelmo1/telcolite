package ba.edi.telcolite.customer;

import java.time.LocalDateTime;

public class Customer {
    private Long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private CustomerStatus status;
    private final LocalDateTime createdAt;

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
            throw new IllegalArgumentException("Customer is already blocked");
        }
        status=CustomerStatus.BLOCKED;
    }

    public void unblock(){
        if(status==CustomerStatus.ACTIVE){
            throw new IllegalArgumentException("Customer is already active");
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
