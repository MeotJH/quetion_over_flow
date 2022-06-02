package com.questionoverflow.qof.common.webConfig;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

public interface WebConfig extends WebMvcConfigurer {

    @Override
    default void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    }

    @Override
    default void addCorsMappings(CorsRegistry registry) {
    }

}
