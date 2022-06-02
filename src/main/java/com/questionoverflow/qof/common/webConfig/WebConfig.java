package com.questionoverflow.qof.common.webConfig;

import com.questionoverflow.qof.common.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

public interface WebConfig extends WebMvcConfigurer {

    //  https://www.holaxprogramming.com/2015/10/10/spring-boot-multi-datasources/

    @Override
    default void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    }

    @Override
    default void addCorsMappings(CorsRegistry registry) {
    }

}
