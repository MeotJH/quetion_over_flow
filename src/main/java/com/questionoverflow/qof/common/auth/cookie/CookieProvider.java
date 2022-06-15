package com.questionoverflow.qof.common.auth.cookie;

import com.questionoverflow.qof.common.auth.dto.JsonWebToken;
import com.questionoverflow.qof.common.auth.jwt.JwtProvider;
import com.questionoverflow.qof.common.exception.CommonException;
import com.questionoverflow.qof.common.exception.ErrorMessage;
import lombok.Builder;

import javax.servlet.http.Cookie;

public class CookieProvider {

    private JsonWebToken jsonWebToken;
    private Cookie cookie;

    @Builder
    public CookieProvider(JsonWebToken jsonWebToken){
        this.jsonWebToken = jsonWebToken;
    }


    public Cookie bakeCookie(){
        cookie = new Cookie("RefreshToken",jsonWebToken.getRefreshToken());

        cookie.setMaxAge(7 * 24 * 60 * 60);

        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    public static Cookie refreshCookie(Cookie[] cookies, JwtProvider jwtProvider){

        for (Cookie cookie : cookies) {
            if( cookie.getName() == "RefreshToken" ){

                if (jwtProvider.verifyToken(cookie.getValue())) {
                    String email = jwtProvider.getEmail(cookie.getValue());
                    JsonWebToken jsonWebToken = jwtProvider.generateToken(email);

                    CookieProvider provider = CookieProvider.builder().jsonWebToken(jsonWebToken).build();
                    Cookie bakedCookie = provider.bakeCookie();

                    return bakedCookie;
                }
            }
        }

        throw new CommonException(ErrorMessage.NO_JWT_TOKEN);
    }

}
