package com.questionoverflow.qof.common.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class JwtProvider {

    private String JWT_SECRET;

    private long tokenPeriod = 1000L * 60L * 10L;

    private long refreshPeriod = 1000L * 60L * 60L * 24L * 30L * 3L;

    private String accessToken;

    private Date expiryDate;

    /*@Builder
    public JwtProvider(String accessToken, Instant authDate ){

        this.accessToken = accessToken;
        this.authDate = Date.from(authDate);
        this.expiryDate = new Date(this.authDate.getTime() + JWT_EXPIRATION_MS);
    }*/

    public JwtProvider(@Value("${jwt.secret}") String JWT_SECRET ){
        this.JWT_SECRET = JWT_SECRET;
    }

    public JsonWebToken generateToken(String uid) { // TO_DO 여기 argu로 role이 있었음

        Claims claims = Jwts.claims().setSubject(uid);
        //claims.put("role", role);

        Date now = new Date();

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenPeriod))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();

        return JsonWebToken.builder()
                .accessToken(jwt)
                .build();
    }

}
