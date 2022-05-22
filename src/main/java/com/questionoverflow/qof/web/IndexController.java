package com.questionoverflow.qof.web;


import com.questionoverflow.qof.common.auth.LoginUser;
import com.questionoverflow.qof.common.auth.dto.SessionUser;
import com.questionoverflow.qof.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public SessionUser index(@LoginUser SessionUser user){
        return user;
    }
}
