package com.wolves.mainproject.dto.request;

import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.type.RoleType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RequestSignupDto {
    private String username;
    private String password;
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
}
