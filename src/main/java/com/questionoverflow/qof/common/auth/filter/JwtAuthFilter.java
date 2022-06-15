package com.questionoverflow.qof.common.auth.filter;

import com.questionoverflow.qof.common.auth.jwt.JwtProvider;
import com.questionoverflow.qof.common.exception.CommonException;
import com.questionoverflow.qof.common.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

            String token = ((HttpServletRequest) request).getHeader("Authorization");

            //Exception처리 만들어야함 TODO

            if( token != null){
                token = token.replace("Bearer ","");
            }

            if( token != null && jwtProvider.verifyToken(token) ){
                Authentication authentication = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            chain.doFilter(request, response);


    }
}
