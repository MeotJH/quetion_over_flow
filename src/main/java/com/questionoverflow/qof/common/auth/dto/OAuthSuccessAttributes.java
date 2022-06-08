package com.questionoverflow.qof.common.auth.dto;

import com.questionoverflow.qof.common.auth.jwt.JwtProvider;
import com.questionoverflow.qof.domain.user.Role;
import com.questionoverflow.qof.domain.user.Users;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthSuccessAttributes {

    private String name;
    private String email;
    private String picture;
    private JsonWebToken token;

    @Builder
    public OAuthSuccessAttributes(Map<String, Object> attributes, JwtProvider jwt){
        this.name = attributes.get("name").toString();
        this.email = attributes.get("email").toString();
        this.picture = attributes.get("picture").toString();
        this.token = jwt.generateToken(this.email);
    }

    public Users toEntity(){
        return Users.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .accessToken(token.getAccessToken())
                .role(Role.GUEST)
                .build();
    }
}
