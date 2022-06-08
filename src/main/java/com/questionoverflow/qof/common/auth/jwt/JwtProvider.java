package com.questionoverflow.qof.common.auth.jwt;

import com.questionoverflow.qof.common.auth.dto.JsonWebToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    private String JWT_SECRET;

    private long tokenPeriod = 1000L * 60L * 10L;

    private long refreshPeriod = 1000L * 60L * 60L * 24L * 30L * 3L;

    private String accessToken;

    private Date expiryDate;

    public JwtProvider(@Value("${jwt.secret}") String JWT_SECRET ){
        this.JWT_SECRET = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes());
    }

    public JsonWebToken generateToken(String uid) { // TO_DO 여기 argu로 role이 있었음

        Claims claims = Jwts.claims().setSubject(uid);

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

    public boolean verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token);
            return claims.getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmail(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
    }

}
