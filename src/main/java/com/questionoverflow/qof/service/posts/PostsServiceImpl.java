package com.questionoverflow.qof.service.posts;

import com.questionoverflow.qof.domain.posts.Posts;
import com.questionoverflow.qof.domain.posts.PostsRepository;
import com.questionoverflow.qof.common.exception.NoPostException;
import com.questionoverflow.qof.web.dto.post.PostsListResponseDto;
import com.questionoverflow.qof.web.dto.post.PostsResponseDto;
import com.questionoverflow.qof.web.dto.post.PostsSaveRequestDto;
import com.questionoverflow.qof.web.dto.post.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsServiceImpl implements PostsService {

    private final PostsRepository postsRepository;

    @Override
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save( requestDto.toEntity() ).getId();
    }

    @Override
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new NoPostException(id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Override
    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new NoPostException(id));
        return new PostsResponseDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAll() {
        List<PostsListResponseDto> all = postsRepository.findAll().stream().map(PostsListResponseDto::new).collect(Collectors.toList());

        if( all == null ){
            new IllegalArgumentException("불러올 게시글이 없습니다.");
        }

        return all;
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new NoPostException(id));
        postsRepository.delete(posts);
        return id;
    }

    @Override
    @Transactional
    public Long updateViewCnt(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new NoPostException(id));
        posts.updateViewCnt( posts.getViewCnt()+1L );
        return posts.getViewCnt()+1L;
    }


}
