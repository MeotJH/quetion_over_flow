package com.questionoverflow.qof.common.auth.jwt;

import com.questionoverflow.qof.common.auth.dto.JsonWebToken;
import com.questionoverflow.qof.domain.user.UserRepository;
import com.questionoverflow.qof.domain.user.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProviderImpl implements JwtProvider{

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    private long tokenPeriod = 1000L * 60L * 10L;

    private long refreshPeriod = 1000L * 60L * 60L * 24L * 30L * 3L;

    private String accessToken;

    private Date expiryDate;

    private final UserRepository userRepository;

    @Override
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

    @Override
    public boolean verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token);
            return claims.getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getEmail(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    @Override
    public Authentication getAuthentication(String jwtToken) {
        String email = getEmail(jwtToken);
        Users users = userRepository.findByEmail(email).get();
        return new UsernamePasswordAuthenticationToken(users,"", Arrays.asList(new SimpleGrantedAuthority(users.getRoleKey())));
    }


}
