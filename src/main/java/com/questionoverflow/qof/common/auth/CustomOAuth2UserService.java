package com.questionoverflow.qof.common.auth;

import com.questionoverflow.qof.common.auth.dto.OAuthAttributes;
import com.questionoverflow.qof.common.auth.dto.SessionUser;
import com.questionoverflow.qof.domain.user.Users;
import com.questionoverflow.qof.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //LocalDateTime ld = LocalDateTime.from(userRequest.getAccessToken().getIssuedAt());



        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        String accessToken = userRequest.getAccessToken().getTokenValue();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, accessToken ,userNameAttributeName, oAuth2User.getAttributes());

        Users users = saveOrUpdate(attributes);
        
        //session에 저장하는 부분 없애고 jwt로 관리
        httpSession.setAttribute("user", new SessionUser(users));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(users.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }


    private Users saveOrUpdate(OAuthAttributes attributes) {
        Users users = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture(), attributes.getAccessToken()))
                .orElse(attributes.toEntity());

        return userRepository.save(users);
    }
}