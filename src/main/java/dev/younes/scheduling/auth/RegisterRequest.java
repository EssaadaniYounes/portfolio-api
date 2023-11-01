package dev.younes.scheduling.auth;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
