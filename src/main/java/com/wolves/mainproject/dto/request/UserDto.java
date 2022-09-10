package com.wolves.mainproject.dto.request;

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
}
