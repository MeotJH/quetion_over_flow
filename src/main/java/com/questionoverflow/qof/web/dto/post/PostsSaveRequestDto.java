package com.questionoverflow.qof.web.dto.post;

import com.questionoverflow.qof.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

    private String title;
    private String cardContent;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String cardContent, String content, String author){
        this.title = title;
        this.cardContent = cardContent;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .cardContent(cardContent)
                .content(content)
                .author(author)
                .build();
    }
}
