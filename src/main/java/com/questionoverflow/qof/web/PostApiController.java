package com.questionoverflow.qof.web;

import com.questionoverflow.qof.service.posts.PostsService;
import com.questionoverflow.qof.web.dto.post.PostsListResponseDto;
import com.questionoverflow.qof.web.dto.post.PostsResponseDto;
import com.questionoverflow.qof.web.dto.post.PostsSaveRequestDto;
import com.questionoverflow.qof.web.dto.post.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById (@PathVariable Long id){
        return postsService.findById(id);
    }

    @GetMapping("/api/v1/posts")
    public List<PostsListResponseDto> findAll(){
        return postsService.findAll();
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long Delete(@PathVariable Long id){
        return postsService.delete(id);
    }

    @PatchMapping("/api/v1/posts/{id}")
    public Long updateViewCnt(@PathVariable Long id){
        return postsService.updateViewCnt(id);
    }



}
