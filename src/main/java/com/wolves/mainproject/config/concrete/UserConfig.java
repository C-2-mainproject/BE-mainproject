package com.wolves.mainproject.config.concrete;

import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.type.RoleType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description : 초기 실행 시 User, Admin 강제 주입
 * @Author : 장동하
 **/
@Component
public class UserConfig {

    public UserConfig(UserRepository userRepository){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        insertUser(userRepository, encoder);
    }

    @Transactional
    void insertUser(UserRepository userRepository, BCryptPasswordEncoder encoder){
        User user = makeUser(encoder);
        User admin = makeAdmin(encoder);

        userRepository.save(user);
        userRepository.save(admin);
    }

    private User makeUser(BCryptPasswordEncoder encoder){
        return User.builder()
                .username("user@user.com")
                .nickname("user")
                .password(encoder.encode("1234"))
                .profileImage("test.png")
                .role(RoleType.ROLE_USER)
                .build();
    }

    private User makeAdmin(BCryptPasswordEncoder encoder){
        return User.builder()
                .username("admin@admin.com")
                .nickname("admin")
                .password(encoder.encode("1234"))
                .profileImage("test.png")
                .role(RoleType.ROLE_ADMIN)
                .build();
    }
}
