package com.wolves.mainproject.config.oauth;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.config.oauth.provider.GoogleUserInfo;
import com.wolves.mainproject.config.oauth.provider.NaverUserInfo;
import com.wolves.mainproject.config.oauth.provider.OAuth2BaseUserInfo;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.type.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2BaseUserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User, userRequest);
        }
        if (userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oAuth2UserInfo = new NaverUserInfo(oAuth2User, userRequest, oAuth2User.getAttribute("response"));
        }

        String provider = oAuth2UserInfo.getProvider();
        String email = oAuth2UserInfo.getEmail();
        String password = oAuth2UserInfo.getPassword(bCryptPasswordEncoder);
        String nickname = oAuth2UserInfo.getUsername();
        RoleType role = oAuth2UserInfo.getRole();


        User userEntity = userRepository.findByUsername(email).orElse(null);


        User user = User.builder()
                .username(email)
                .nickname(nickname)
                .password(password)
                .profileImage("")
                .role(role)
                .provider(provider)
                .build();


        if (userEntity == null){
            userRepository.save(user);
            userEntity = user;
        }


        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }

}
