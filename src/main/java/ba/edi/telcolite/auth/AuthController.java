package ba.edi.telcolite.auth;

import ba.edi.telcolite.subscription.SubscriptionResponse;
import ba.edi.telcolite.subscription.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AccessGuard accessGuard;
    private final SubscriptionService subscriptionService;

    public AuthController(AuthService authService,
                          AccessGuard accessGuard,
                          SubscriptionService subscriptionService) {
        this.authService = authService;
        this.accessGuard=accessGuard;
        this.subscriptionService=subscriptionService;
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/api/customers/{customerId}/subscriptions")
    public List<SubscriptionResponse> findAllByCustomer(@PathVariable Long customerId,
                                                        @AuthenticationPrincipal Jwt jwt) {
        accessGuard.checkCustomerAccess(jwt, customerId);
        return subscriptionService.getAllByCustomerId(customerId);
    }
}