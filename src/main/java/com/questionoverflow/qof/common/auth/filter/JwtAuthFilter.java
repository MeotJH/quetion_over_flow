package com.questionoverflow.qof.common.auth.filter;

import com.questionoverflow.qof.common.auth.jwt.JwtProvider;
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
import java.util.Enumeration;

@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        Enumeration<String> headerNames = request1.getHeaderNames();

        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                System.out.println("Header: " + request1.getHeader(headerNames.nextElement()));
            }
        }

        String token = ((HttpServletRequest) request).getHeader("Authorization");

        if( token != null ){
            token = token.replace("Bearer ","");
        }

        if( token != null && jwtProvider.verifyToken(token) ){
            Authentication authentication = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
