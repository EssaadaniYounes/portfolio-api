package dev.younes.scheduling.auth;

public record LoginRequest(
        String email,
        String password
) {
}
