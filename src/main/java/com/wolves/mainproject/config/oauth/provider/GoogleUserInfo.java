package com.wolves.mainproject.config.oauth.provider;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import com.wolves.mainproject.type.RoleType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class GoogleUserInfo implements OAuth2UserInfo, Observer{
    private final String providerName = "google";
    private final OAuth2User oAuth2User;
    private final OAuth2UserRequest userRequest;
    private final OAuth2BaseUserInfo oAuth2BaseUserInfo;


    public GoogleUserInfo(OAuth2BaseUserInfo oAuth2BaseUserInfo){
        this.oAuth2BaseUserInfo = oAuth2BaseUserInfo;
        oAuth2User = oAuth2BaseUserInfo.getOAuth2User();
        userRequest = oAuth2BaseUserInfo.getUserRequest();
        oAuth2BaseUserInfo.registerObserver(this);
    }

    @Override
    public String getProviderId() {
        return oAuth2User.getAttribute("sub");
    }

    @Override
    public String getProvider() {
        return userRequest.getClientRegistration().getRegistrationId();
    }

    @Override
    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }

    @Override
    public String getUsername() {
        return oAuth2User.getAttribute("name");
    }

    @Override
    public String getProfileImage() {
        return oAuth2User.getAttribute("picture");
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
            oAuth2BaseUserInfo.setOAuth2UserInfo(this);
        }

    }
}
