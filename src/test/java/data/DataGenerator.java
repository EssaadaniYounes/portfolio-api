package data;

import dev.younes.portfolio.skill.Skill;
import dev.younes.portfolio.user.User;

import java.util.ArrayList;

public class DataGenerator {

    public static Skill createSkillA(){
       return Skill.builder()
               .id(1L)
               .name("HTML")
               .users(null)
               .build();

    }
    public static Skill createSkillB(){
        return Skill.builder()
                .id(2L)
                .name("CSS")
                .users(null)
                .build();
    }
    public static Skill createSkillC(){
        return Skill.builder()
                .id(3L)
                .name("JS")
                .users(null)
                .build();
    }

    public static User createUserA(){
        return User.builder()
                .id(1)
                .email("younes@gmail.com")
                .password("1234")
                .build();
    }
    public static User createUserB(){
        return User.builder()
                .id(2)
                .firstName("younes")
                .lastName("essaadani")
                .bio("Full stack dev")
                .email("younes@gmail.com")
                .skills(null)
                .build();
    }

}
