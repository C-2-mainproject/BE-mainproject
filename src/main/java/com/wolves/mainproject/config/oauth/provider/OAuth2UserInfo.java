package com.wolves.mainproject.config.oauth.provider;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getUsername();
    String getProfileImage();
}
