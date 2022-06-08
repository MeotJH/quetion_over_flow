package com.questionoverflow.qof.common.auth.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JsonWebToken {

    private String accessToken;

    @Builder
    public JsonWebToken(String accessToken){
        this.accessToken = accessToken;
    }
}
