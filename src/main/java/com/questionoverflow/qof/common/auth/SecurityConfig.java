package com.questionoverflow.qof.common.auth;

import com.questionoverflow.qof.common.auth.filter.JwtAuthFilter;
import com.questionoverflow.qof.common.auth.jwt.JwtProvider;
import com.questionoverflow.qof.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler successHandler;

    private final JwtProvider jwtProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()// 기본 인증 방식 STATELESS
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile", "/api/v1/posts/**", "/api/v1/post").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    .anyRequest().authenticated()
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                    .successHandler(successHandler)
                    .userInfoEndpoint()
                    .userService(customOAuth2UserService);

        http.addFilterBefore(new JwtAuthFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
