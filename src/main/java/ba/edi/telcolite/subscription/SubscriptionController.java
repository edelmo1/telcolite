package ba.edi.telcolite.subscription;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/api/customers/{customerId}/subscriptions")
    public ResponseEntity<SubscriptionResponse> create(@PathVariable Long customerId,
                                                       @RequestBody @Valid CreateSubscriptionRequest request) {
        SubscriptionResponse created = subscriptionService.create(customerId, request);
        return ResponseEntity.created(URI.create("/api/subscriptions/" + created.id())).body(created);
    }

    @GetMapping("/api/customers/{customerId}/subscriptions")
    public List<SubscriptionResponse> findAllByCustomer(@PathVariable Long customerId) {
        return subscriptionService.getAllByCustomerId(customerId);
    }

    @GetMapping("/api/subscriptions/{id}")
    public SubscriptionResponse getById(@PathVariable Long id) {
        return subscriptionService.getById(id);
    }

    @PostMapping("/api/subscriptions/{id}/suspend")
    public SubscriptionResponse suspend(@PathVariable Long id) {
        return subscriptionService.suspend(id);
    }

    @PostMapping("/api/subscriptions/{id}/resume")
    public SubscriptionResponse resume(@PathVariable Long id) {
        return subscriptionService.resume(id);
    }

    @PostMapping("/api/subscriptions/{id}/terminate")
    public SubscriptionResponse terminate(@PathVariable Long id) {
        return subscriptionService.terminate(id);
    }
}