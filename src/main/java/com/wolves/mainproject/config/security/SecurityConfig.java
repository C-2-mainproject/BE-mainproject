package com.wolves.mainproject.config.security;

import com.wolves.mainproject.config.oauth.PrincipalOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {
    private final CorsConfig config;
    private final PrincipalOAuth2UserService principalOAuth2UserService;
    private final OAuth2SuccessHandler successHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .formLogin().disable()
                .apply(new MyCustomDsl())
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(principalOAuth2UserService)
                .and()
                .successHandler(successHandler)
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/")
                .and()
                .build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http){
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(config.corsFilter())
                    .addFilter(new SessionAuthenticationFilter(authenticationManager));
        }
    }

}