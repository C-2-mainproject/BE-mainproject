package com.wolves.mainproject.dto.request;

import com.wolves.mainproject.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PasswordDto {

    private String password;

    public User toUser(String password,
                       User user){
        return User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(password)
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .provider(user.getProvider())
                .role(user.getRole())
                .build();
    }
}
