package com.wolves.mainproject.config.security;

import com.wolves.mainproject.config.oauth.PrincipalOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import java.net.MalformedURLException;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {
    private final CorsConfig config;
    private final PrincipalOAuth2UserService principalOAuth2UserService;

    @Bean
    public CookieSerializer cookieSerializer() throws MalformedURLException {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setSameSite("none");
        serializer.setUseSecureCookie(true);
        return serializer;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf().disable()
                .cors()
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .apply(new MyCustomDsl())
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(principalOAuth2UserService)
                .and()
                .and()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http){
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(config.corsFilter())
                    .addFilter(new SessionAuthenticationFilter(authenticationManager))
                    .addFilter(new SessionAuthorizationFilter(authenticationManager));
        }
    }

}