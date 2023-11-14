package data;

import dev.younes.portfolio.user.Role;
import dev.younes.portfolio.user.User;

public class MockData {
    public static User getUserA(){
        return User.builder()
                .id(1)
                .firstName("Younes")
                .lastName("Essaadani")
                .email("random@gmail.com")
                .role(Role.USER)
                .password("1234")
                .build();
    }

    public static User getUserB(){
        return User.builder()
                .id(2)
                .firstName("Younes")
                .lastName("Essaadani")
                .email("random2@gmail.com")
                .role(Role.USER)
                .password("1234")
                .build();
    }
}
