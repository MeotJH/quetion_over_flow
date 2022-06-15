package com.questionoverflow.qof.common.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JsonWebToken {

    private String accessToken;

    private String refreshToken;

    @Builder
    public JsonWebToken(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
