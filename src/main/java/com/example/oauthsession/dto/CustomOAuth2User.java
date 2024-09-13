package com.example.oauthsession.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final OAuth2Response oAuth2Respone;
    private final String role;

    public CustomOAuth2User(OAuth2Response oAuth2Response, String role) {
        this.oAuth2Respone = oAuth2Response;
        this.role = role;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null; //google, naver 형식 달라서 일단 null
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return role;
            }
        });

        return collection;
    }

    @Override
    public String getName() {
        return oAuth2Respone.getName();
    }

    public String getUsername() {
        return oAuth2Respone.getProvider() + " " + oAuth2Respone.getProviderId();
    }
}
