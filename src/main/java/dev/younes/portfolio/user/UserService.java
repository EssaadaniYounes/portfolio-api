package dev.younes.portfolio.user;

import dev.younes.portfolio.auth.AuthenticationService;
import dev.younes.portfolio.exceptions.NotFoundException;
import dev.younes.portfolio.skill.Skill;
import dev.younes.portfolio.skill.SkillRepository;
import dev.younes.portfolio.skill.SkillService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final SkillService skillService;

    @Autowired
    public UserService(
            AuthenticationService authenticationService,
            UserRepository userRepository,
            SkillService skillService) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.skillService = skillService;
    }

    public boolean userExistsByEmail(String email){
        return userRepository.existsUserByEmail(email);
    }

    @Transactional
    public User editProfile(EditUserDao data, String authEmail){

        User user = userRepository.findByEmail(authEmail)
                .orElseThrow(()-> new NotFoundException("User not found"));
        if(data.firstName() != null) user.setFirstName(data.firstName());
        if(data.lastName() != null) user.setLastName(data.lastName());
        //TODO: Verify email not taken and Regenerate Token if email changed
        if(data.email() != null) user.setEmail(data.email());
        if(data.bio() != null) user.setBio(data.bio());
        if(data.profileURL() != null) user.setProfile(data.profileURL());

        assignSkills(data.skills(), user);
        return user;
    }

    public void assignSkills(String[] skills, User user){
       List<Skill> stored =  Arrays
                                .stream(skills)
                                .map( s ->
                                        skillService.saveSkill(
                                            Skill.builder()
                                                    .name(s)
                                                    .build()
                                )).toList();
        user.setSkills(stored);
    }

    public UserDao getProfile(User user) {

        return UserDao.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .bio(user.getBio())
                .profileUrl(user.getProfile())
                .build();
    }
}
