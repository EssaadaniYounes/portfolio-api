package dev.younes.portfolio.auth;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
