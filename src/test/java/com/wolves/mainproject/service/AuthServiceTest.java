package com.wolves.mainproject.service;

import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.dto.request.my.word.storage.RequestSignupDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthServiceTest {
    @Autowired
    private UserRepository userRepository;

    User getUser(){
        RequestSignupDto dto = RequestSignupDto.builder()
                .username("jdh3340@naver.com")
                .password("1234")
                .nickname("nickname")
                .profileImage("image.png")
                .ageGroup(10)
                .gender("남")
                .build();
        return dto.toUser();
    }

    @BeforeEach
    void registerUser(){
        User user = getUser();

        userRepository.save(user);
    }

    /**
     * @Description : 이메일 중복 검사
     * @return : 중복이 아닐 시 null 반환
     * @Author : Jangdongha
     **/
    @Test
    void checkEmailTest(){
        // Given
        String notUniqueEmail = "jdh3340@naver.com";
        String uniqueEmail = "jsh3340@naver.com";
        // When
        User userNotUniquePS = userRepository.findByUsername(notUniqueEmail).orElse(null);
        User userUniquePS = userRepository.findByUsername(uniqueEmail).orElse(null);
        // Then
        assert userNotUniquePS != null;
        assertEquals(notUniqueEmail, userNotUniquePS.getUsername());
        assertNull(userUniquePS);
    }

    /**
     * @Description : 닉네임 중복 검사
     * @return : 중복이 아닐 시 null 반환
     * @Author : Jangdongha
     **/
    @Test
    void checkNicknameTest(){
        // Given
        String notUniqueNickname = "nickname";
        String uniqueNickname = "nickname2";
        // When
        User userNotUniquePS = userRepository.findByNickname(notUniqueNickname).orElse(null);
        User userUniquePS = userRepository.findByNickname(uniqueNickname).orElse(null);
        // Then

        assert userNotUniquePS != null;
        assertEquals(notUniqueNickname, userNotUniquePS.getNickname());
        assertNull(userUniquePS);
    }


}
