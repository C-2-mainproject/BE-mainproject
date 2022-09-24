package com.wolves.mainproject.config.oauth;

import com.wolves.mainproject.config.oauth.provider.OAuth2BaseUserInfo;
import com.wolves.mainproject.config.oauth.provider.OAuth2UserInfo;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.type.RoleType;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
public class UserToEntity {
    private String provider;
    private String email;
    private String password;
    private String nickname;
    private String profileImage;
    private RoleType role;


    public void setUserByUserInfo(OAuth2UserInfo oAuth2UserInfo, BCryptPasswordEncoder bCryptPasswordEncoder){
        provider = oAuth2UserInfo.getProvider();
        email = oAuth2UserInfo.getEmail();
        password = oAuth2UserInfo.getPassword(bCryptPasswordEncoder);
        nickname = oAuth2UserInfo.getUsername();
        profileImage = oAuth2UserInfo.getProfileImage();
        role = oAuth2UserInfo.getRole();
    }

    public User getUser(){
        return User.builder()
                .username(email)
                .nickname(nickname)
                .password(password)
                .profileImage(profileImage)
                .role(role)
                .provider(provider)
                .build();
    }

}
