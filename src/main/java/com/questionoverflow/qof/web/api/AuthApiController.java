package com.questionoverflow.qof.web.api;

import com.questionoverflow.qof.web.dto.post.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class AuthApiController {

    @Value("${front-end-url}")
    String url;

    @GetMapping("/api/v1/auth")
    public void findById (@PathVariable String token, HttpServletResponse response) throws IOException {
        //String redirectUrl = url+"?token"+token;
        response.sendRedirect(url);
    }
}
