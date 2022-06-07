package com.questionoverflow.qof.common.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

public class JwtProvider {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Value("${jwt.expiration-ms}")
    private int JWT_EXPIRATION_MS;

    private String userId;

    private String authDate;

    public JwtProvider( String userId, String authDate ){
        this.userId = userId;
        this.authDate = authDate;
    }

    public String generateToken(Authentication authentication) {

        //Date now = new Date();
        //Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);

        /*
        return Jwts.builder()
                .setSubject(userId) // 사용자
                .setIssuedAt(new Date()) // 현재 시간 기반으로 생성
                .setExpiration(expiryDate) // 만료 시간 세팅
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET) // 사용할 암호화 알고리즘, signature에 들어갈 secret 값 세팅
                .compact();*/
        return null;
    }
}
