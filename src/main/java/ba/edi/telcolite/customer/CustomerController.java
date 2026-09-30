package ba.edi.telcolite.customer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService=customerService;
    }

    @GetMapping
    public List<CustomerResponse> findAll(){
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public CustomerResponse getById(@PathVariable Long id){
        return customerService.getById(id);
    }

    @PostMapping
    public CustomerResponse create(@RequestBody CreateCustomerRequest request){
        CustomerResponse created = customerService.create(request);
        return ResponseEntity.created(URI.create("/api/customers/" + created.id())).body(created).getBody();    }

    @PostMapping("/{id}/block")
    public CustomerResponse block(@PathVariable Long id) {
        return customerService.block(id);
    }
    @PostMapping("/{id}/unblock")
    public CustomerResponse unblock(@PathVariable Long id) {
        return customerService.unblock(id);
    }

}
