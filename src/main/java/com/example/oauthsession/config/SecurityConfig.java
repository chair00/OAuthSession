package com.example.oauthsession.config;

import com.example.oauthsession.service.CustomOAuth2UserService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((csrf) -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()).disable());

        http
                .formLogin((login) -> login.disable());

        http
                .httpBasic((basic) -> basic.disable());

        http.
                oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)));

        http.
                headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin));

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers("/", "/oauth2/**", "/login/**").permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }
}
