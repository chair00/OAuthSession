package com.example.oauthsession.dto;

public interface OAuth2Response {

    // naver, google...
    String getProvider();
    String getProviderId();
    String getEmail();
    String getName();
}
