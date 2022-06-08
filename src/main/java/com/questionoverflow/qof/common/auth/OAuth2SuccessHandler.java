package com.questionoverflow.qof.common.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.questionoverflow.qof.common.auth.dto.OAuthSuccessAttributes;
import com.questionoverflow.qof.common.auth.jwt.JwtProvider;
import com.questionoverflow.qof.domain.user.UserRepository;
import com.questionoverflow.qof.domain.user.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;


    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

        OAuthSuccessAttributes attributes = OAuthSuccessAttributes.builder()
                .attributes(oAuth2User.getAttributes())
                .jwt(jwtProvider)
                .build();

        saveOrUpdate(attributes);

        response.sendRedirect("http://localhost:3000/?token="+attributes.getToken().getAccessToken());
    }

    private Users saveOrUpdate(OAuthSuccessAttributes attributes) {
        Users users = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture(), attributes.getToken().getAccessToken() ))
                .orElse(attributes.toEntity());

        return userRepository.save(users);
    }


}
