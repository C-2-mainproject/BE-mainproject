package com.wolves.mainproject.dto.request;

import com.wolves.mainproject.domain.user.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    private String nickname;
    private String profileImage;
    private Integer ageGroup;
    private String gender;


    public User toUser(User user) {
      return User.builder()
              .id(user.getId())
              .username(user.getUsername())
              .password(user.getPassword())
              .nickname(nickname)
              .profileImage(profileImage)
              .ageGroup(ageGroup)
              .gender(gender)
              .provider(user.getProvider())
              .role(user.getRole())
              .build();
    }

}