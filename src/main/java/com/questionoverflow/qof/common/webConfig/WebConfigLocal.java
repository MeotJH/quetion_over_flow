package com.questionoverflow.qof.common.webConfig;

import com.questionoverflow.qof.common.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(name="spring.config.activate.on-profile", havingValue="local")
public class WebConfigLocal implements WebConfig{

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("PATCH","PUT","DELETE","GET","POST")
                .allowCredentials(true)
                .allowedOrigins("http://localhost:3000");

    }

}
