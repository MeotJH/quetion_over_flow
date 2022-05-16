package com.questionoverflow.qof.service.posts;

import com.questionoverflow.qof.web.dto.post.PostsListResponseDto;
import com.questionoverflow.qof.web.dto.post.PostsResponseDto;
import com.questionoverflow.qof.web.dto.post.PostsSaveRequestDto;
import com.questionoverflow.qof.web.dto.post.PostsUpdateRequestDto;

import java.util.List;

public interface PostsService {

    public Long save(PostsSaveRequestDto requestDto);

    public Long update(Long id, PostsUpdateRequestDto requestDto);

    public PostsResponseDto findById(Long id);

    public List<PostsListResponseDto> findAll();

    public Long delete(Long id);
}
