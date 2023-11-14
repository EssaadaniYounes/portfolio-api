package dev.younes.portfolio.auth;

import dev.younes.portfolio.user.EditUserDao;
import dev.younes.portfolio.user.User;
import dev.younes.portfolio.user.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(maxAge = 3600)
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody LoginRequest request
    ){
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @GetMapping("/user")
    public ResponseEntity<EditUserDao> getAuthenticatedUser(Authentication authentication){

        return new ResponseEntity<>(
                authenticationService.getAuthenticatedUserProfile(
                        authenticationService.getAuthenticatedUser(authentication)
                ),
                HttpStatus.OK
        );
    }

}
