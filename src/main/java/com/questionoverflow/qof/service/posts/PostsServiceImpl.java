package com.questionoverflow.qof.service.posts;

import com.questionoverflow.qof.domain.posts.PostsRepository;
import com.questionoverflow.qof.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService {

    private final PostsRepository postsRepository;

    @Override
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save( requestDto.toEntity() ).getId();
    }
}
