package com.wolves.mainproject.config.oauth.provider;

import com.wolves.mainproject.type.RoleType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Getter
@Setter
public class OAuth2BaseUserInfo implements Subject {
    private OAuth2User oAuth2User;
    private OAuth2UserRequest userRequest;
    private OAuth2UserInfo oAuth2UserInfo;
    private final List<Observer> observers;
    private String providerName;
    public OAuth2BaseUserInfo(OAuth2User oAuth2User, OAuth2UserRequest userRequest){
        observers = new ArrayList<>();
        this.oAuth2User = oAuth2User;
        this.userRequest = userRequest;
    }

    @Override
    public void registerObserver(Observer o){
        observers.add(o);
    }

    @Override
    public void notifyObservers(){
        observers.forEach(o -> o.setOAuth2UserInfo(providerName));
    }

    public void findOAuthType(String providerName){
        this.providerName = providerName;
        notifyObservers();
    }
}
