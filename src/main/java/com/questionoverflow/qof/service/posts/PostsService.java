package com.questionoverflow.qof.service.posts;

import com.questionoverflow.qof.web.dto.PostsSaveRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface PostsService {

    public Long save(PostsSaveRequestDto requestDto);
}
