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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    public JsonWebToken generateToken(String uid) { // TODO 여기 argu로 role이 있었음

        Claims claims = Jwts.claims().setSubject(uid);

        Date now = new Date();
        HashMap<String,JsonWebToken> tokenHm = new HashMap<>();

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenPeriod))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();

        String refreshJwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshPeriod))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();

        return JsonWebToken.builder()
                .accessToken(jwt)
                .refreshToken(refreshJwt)
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

    @Override
    public JsonWebToken refreshToken(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        JsonWebToken jsonWebToken = null;

        for (Cookie cookie : cookies) {

            if( cookie.getName().equals("RefreshToken") &&  verifyToken(cookie.getValue() ) == true){
                jsonWebToken = generateToken(getEmail(cookie.getValue()));
            }

        }

        return jsonWebToken;
    }


}
