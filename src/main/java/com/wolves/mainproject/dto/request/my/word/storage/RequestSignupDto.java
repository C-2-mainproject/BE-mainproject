package com.wolves.mainproject.dto.request.my.word.storage;

import com.wolves.mainproject.domain.game.history.GameHistory;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.exception.ErrorCode;
import com.wolves.mainproject.handler.aop.annotation.domain.EntityValidation;
import com.wolves.mainproject.type.RoleType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RequestSignupDto {
    @EntityValidation(length = 100, exception = ErrorCode.EMAIL_TOO_LARGE)
    private String username;
    private String password;
    @EntityValidation(length = 30, exception = ErrorCode.NICKNAME_TOO_LARGE)
    private String nickname;
    private String profileImage;
    private int ageGroup;
    private String gender;

    public User toUser(){
        return User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .profileImage(profileImage)
                .ageGroup(ageGroup)
                .gender(gender)
                .role(RoleType.ROLE_USER)
                .build();
    }

    public GameHistory toGameHistory(User user){
        return GameHistory.builder()
                .user(user)
                .build();
    }
}
