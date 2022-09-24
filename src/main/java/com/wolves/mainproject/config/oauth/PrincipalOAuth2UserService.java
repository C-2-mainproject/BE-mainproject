package com.wolves.mainproject.config.oauth;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.config.oauth.provider.GoogleUserInfo;
import com.wolves.mainproject.config.oauth.provider.NaverUserInfo;
import com.wolves.mainproject.config.oauth.provider.OAuth2BaseUserInfo;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2BaseUserInfo oAuth2BaseUserInfo = new OAuth2BaseUserInfo(oAuth2User, userRequest);
        registerObserver(oAuth2BaseUserInfo);

        String oAuthType = userRequest.getClientRegistration().getRegistrationId();
        oAuth2BaseUserInfo.findOAuthType(oAuthType);

        UserToEntity userToEntity = new UserToEntity();
        userToEntity.setUserByUserInfo(oAuth2BaseUserInfo.getOAuth2UserInfo(), bCryptPasswordEncoder);


        User user = userToEntity.getUser();
        if (!userRepository.existsByUsername(userToEntity.getEmail())){
            userRepository.save(user);
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }

    private void registerObserver(OAuth2BaseUserInfo oAuth2BaseUserInfo){
        new GoogleUserInfo(oAuth2BaseUserInfo);
        new NaverUserInfo(oAuth2BaseUserInfo);
    }




}
