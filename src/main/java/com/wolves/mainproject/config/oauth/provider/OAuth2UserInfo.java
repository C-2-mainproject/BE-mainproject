package com.wolves.mainproject.config.oauth.provider;

import com.wolves.mainproject.type.RoleType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getUsername();
    String getProfileImage();
    RoleType getRole();
    String getPassword(BCryptPasswordEncoder bCryptPasswordEncoder);
    String getProviderName();
}
