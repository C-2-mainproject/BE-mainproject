package com.wolves.mainproject.config.oauth.provider;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import com.wolves.mainproject.type.RoleType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.LinkedHashMap;
import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo, Observer{
    private final String providerName = "naver";
    private Map<String, String> userInfo;
    private final OAuth2User oAuth2User;
    private final OAuth2UserRequest userRequest;
    private final OAuth2BaseUserInfo oAuth2BaseUserInfo;

    public NaverUserInfo(OAuth2BaseUserInfo oAuth2BaseUserInfo){
        this.oAuth2BaseUserInfo = oAuth2BaseUserInfo;
        oAuth2User = oAuth2BaseUserInfo.getOAuth2User();
        userRequest = oAuth2BaseUserInfo.getUserRequest();
        oAuth2BaseUserInfo.registerObserver(this);
    }

    @Override
    public String getProviderId() {
        return userInfo.get("id");
    }
    @Override
    public String getProvider() {
        return userRequest.getClientRegistration().getRegistrationId();
    }

    @Override
    public String getEmail() {
        return userInfo.get("email");
    }

    @Override
    public String getUsername() {
        return userInfo.get("nickname");
    }

    @Override
    public String getProfileImage() {
        return userInfo.get("profile_image");
    }

    @Override
    public RoleType getRole() {
        return RoleType.ROLE_USER;
    }

    @Override
    public String getPassword(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return bCryptPasswordEncoder.encode(providerName);
    }

    @Override
    public String getProviderName() {
        return providerName;
    }

    @Override
    public void setOAuth2UserInfo(String providerName) {
        if (this.providerName.equals(providerName)){
            this.oAuth2BaseUserInfo.setOAuth2UserInfo(this);
            userInfo = oAuth2User.getAttribute("response");
        }
    }
}
