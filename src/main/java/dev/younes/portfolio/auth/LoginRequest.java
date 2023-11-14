package dev.younes.portfolio.auth;

public record LoginRequest(
        String email,
        String password
) {
}
