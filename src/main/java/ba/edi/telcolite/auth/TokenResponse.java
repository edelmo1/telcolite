package ba.edi.telcolite.auth;

public record TokenResponse(String token, long expiresInSeconds) {
}