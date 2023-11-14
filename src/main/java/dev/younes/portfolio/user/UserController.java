package dev.younes.portfolio.user;

import dev.younes.portfolio.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(maxAge = 3600)
public class UserController {
    @Autowired
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping("data")
    public ResponseEntity<?> getUserData(){
        User user = authenticationService.getAuthenticatedUser(
                SecurityContextHolder.getContext().getAuthentication()
        );
        UserDao userDao = userService.getProfile(user);
        return new ResponseEntity<>(userDao, HttpStatus.OK);
    }

    @PostMapping("edit-profile")
    public ResponseEntity<?> EditProfile(@RequestBody EditUserDao payload){
        User authUser = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = userService.editProfile(payload, authUser.getEmail());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
