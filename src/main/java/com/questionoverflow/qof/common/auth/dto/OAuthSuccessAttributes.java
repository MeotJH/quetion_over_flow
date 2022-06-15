package com.questionoverflow.qof.common.auth.dto;

import com.questionoverflow.qof.common.auth.jwt.JwtProviderImpl;
import com.questionoverflow.qof.domain.user.Role;
import com.questionoverflow.qof.domain.user.Users;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class OAuthSuccessAttributes {

    private String name;
    private String email;
    private String picture;
    private JsonWebToken tokens;

    @Builder
    public OAuthSuccessAttributes(Map<String, Object> attributes, JwtProviderImpl jwt){
        this.name = attributes.get("name").toString();
        this.email = attributes.get("email").toString();
        this.picture = attributes.get("picture").toString();
        this.tokens = jwt.generateToken(this.email);
    }

    public Users toEntity(){
        return Users.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .accessToken(tokens.getAccessToken())
                .role(Role.GUEST)
                .build();
    }
}
