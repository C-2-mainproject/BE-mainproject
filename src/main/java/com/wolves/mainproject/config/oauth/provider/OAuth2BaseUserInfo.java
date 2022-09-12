package com.wolves.mainproject.config.oauth.provider;

import com.wolves.mainproject.type.RoleType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public abstract class OAuth2BaseUserInfo implements OAuth2UserInfo{
    public String getPassword(BCryptPasswordEncoder bCryptPasswordEncoder){
        return bCryptPasswordEncoder.encode("mainproject");
    }

    public RoleType getRole(){
        return RoleType.ROLE_USER;
    }
}
