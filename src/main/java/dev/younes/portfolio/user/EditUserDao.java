package dev.younes.portfolio.user;

public record EditUserDao (
        String firstName,
        String lastName,
        String email,
        String bio,
        String profileURL,
        String[] skills
){
}
