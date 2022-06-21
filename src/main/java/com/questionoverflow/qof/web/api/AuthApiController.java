package com.questionoverflow.qof.web.api;

import com.questionoverflow.qof.common.auth.cookie.CookieProvider;
import com.questionoverflow.qof.common.auth.dto.JsonWebToken;
import com.questionoverflow.qof.common.auth.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class AuthApiController {

    @Value("${front-end-url}")
    String url;

    private final JwtProvider jwtProvider;

    @GetMapping("/api/v1/auth/refresh")
    public String refreshAuth(HttpServletRequest request, HttpServletResponse response) {
        JsonWebToken jsonWebToken = jwtProvider.refreshToken(request);

        Cookie cookie = CookieProvider
                            .builder()
                            .jsonWebToken(jsonWebToken)
                            .build()
                                .bakeCookie();

        response.addCookie(cookie);

        return jsonWebToken.getAccessToken();
    }
}
