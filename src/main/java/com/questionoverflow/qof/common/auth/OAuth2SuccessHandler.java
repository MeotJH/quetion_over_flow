package com.questionoverflow.qof.common.auth;

import com.questionoverflow.qof.common.auth.cookie.CookieProvider;
import com.questionoverflow.qof.common.auth.dto.OAuthSuccessAttributes;
import com.questionoverflow.qof.common.auth.jwt.JwtProviderImpl;
import com.questionoverflow.qof.domain.user.UserRepository;
import com.questionoverflow.qof.domain.user.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtProviderImpl jwtProvider;

    @Value("${front-end-url}")
    String url;


    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

        OAuthSuccessAttributes attributes = OAuthSuccessAttributes.builder()
                .attributes(oAuth2User.getAttributes())
                .jwt(jwtProvider)
                .build();

        saveOrUpdate(attributes);
        String contextPath = request.getContextPath();

        CookieProvider cookieProvider = CookieProvider.builder()
                                            .jsonWebToken(attributes.getTokens())
                                            .build();
        Cookie cookie = cookieProvider.bakeCookie();

        response.addCookie(cookie);
        response.sendRedirect(url+"/?token="+attributes.getTokens().getAccessToken());
    }

    private Users saveOrUpdate(OAuthSuccessAttributes attributes) {
        Users users = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture(), attributes.getTokens() ))
                .orElse(attributes.toEntity());

        return userRepository.save(users);
    }


}
