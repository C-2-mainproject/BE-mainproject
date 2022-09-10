package com.wolves.mainproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private String nickname;
    private String profileImage;
    private Integer ageGroup;
    private String gender;

}
