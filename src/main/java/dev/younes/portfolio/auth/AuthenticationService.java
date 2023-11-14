package dev.younes.portfolio.auth;

import dev.younes.portfolio.exceptions.UserAlreadyExistsException;
import dev.younes.portfolio.jwt.JwtService;
import dev.younes.portfolio.skill.Skill;
import dev.younes.portfolio.user.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class AuthenticationService {
    @Autowired
    @Lazy
    private UserService userService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        if(userService.userExistsByEmail(request.email())){
            throw new UserAlreadyExistsException("User with this email exists");
        }
        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public EditUserDao getAuthenticatedUserProfile(User user){

        String[] skills = user.getSkills().stream().map(Skill::getName)
                .toList().toArray(new String[0]);
        log.info("SKILLS :{}", Arrays.toString(skills));
        return new EditUserDao(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getBio(),
                user.getProfile(),
                skills
        );
    }
    public User getAuthenticatedUser(Authentication authentication){
        return (User) authentication.getPrincipal();
    }
}
