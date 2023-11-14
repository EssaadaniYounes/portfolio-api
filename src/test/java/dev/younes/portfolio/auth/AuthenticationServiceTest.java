package dev.younes.portfolio.auth;

import data.MockData;
import dev.younes.portfolio.exceptions.UserAlreadyExistsException;
import dev.younes.portfolio.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthenticationServiceTest {

    private final AuthenticationService underTest;

    @Autowired
    AuthenticationServiceTest(AuthenticationService underTest) {
        this.underTest = underTest;
    }

    @Test
    public void itShouldRegisterUserTest(){
        User user = MockData.getUserA();
        AuthenticationResponse register = underTest.register(
                new RegisterRequest(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPassword()
                )
        );
        assertThat(register).hasFieldOrProperty("token");
    }
    @Test
    public void itShouldThrowEmailExistsExceptionTest(){
        User user = MockData.getUserA();
        RegisterRequest payload = new RegisterRequest(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword()
        );
        AuthenticationResponse register = underTest.register(
                payload
        );
        assertThatThrownBy(()-> underTest.register(
                payload
        ))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining("User with this email exists");
    }
}