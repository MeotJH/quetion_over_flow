package com.questionoverflow.qof.web;


import com.questionoverflow.qof.common.auth.LoginUser;
import com.questionoverflow.qof.common.auth.dto.SessionUser;
import com.questionoverflow.qof.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public void index(@LoginUser SessionUser user, HttpServletResponse response, HttpServletRequest request) throws IOException {
        user = user;
        HttpSession httpSession = request.getSession(true);
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        response.setHeader("user", "helloWorld");
        response.sendRedirect("http://localhost:3000/?token=helloWorld");
    }
}
