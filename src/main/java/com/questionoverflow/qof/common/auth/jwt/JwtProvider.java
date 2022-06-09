package com.questionoverflow.qof.common.auth.jwt;

import com.questionoverflow.qof.common.auth.dto.JsonWebToken;
import org.springframework.security.core.Authentication;

public interface JwtProvider {

    public JsonWebToken generateToken(String uid);

    public boolean verifyToken(String token);

    public String getEmail(String token);

    public Authentication getAuthentication(String jwtToken);
}
