package ba.edi.telcolite.auth;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AccessGuard {

    public void checkCustomerAccess(Jwt jwt, Long customerId) {
        boolean isAdmin = "ADMIN".equals(jwt.getClaimAsString("role"));
        boolean isOwner = Objects.equals(jwt.getSubject(), customerId.toString());
        if (!isAdmin && !isOwner) {
            throw new AccessDeniedException("You can only access your own data");
        }
    }
}