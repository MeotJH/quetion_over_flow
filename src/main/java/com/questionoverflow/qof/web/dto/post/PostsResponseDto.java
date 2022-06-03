package com.questionoverflow.qof.web.dto.post;

import com.questionoverflow.qof.domain.BaseTimeEntity;
import com.questionoverflow.qof.domain.posts.Posts;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    private String createdDate;

    private String modifiedDate;

    public PostsResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.createdDate = BaseTimeEntity.formatDateTime(entity.getCreatedDate());
        this.modifiedDate = BaseTimeEntity.formatDateTime(entity.getModifiedDate());
    }


}
