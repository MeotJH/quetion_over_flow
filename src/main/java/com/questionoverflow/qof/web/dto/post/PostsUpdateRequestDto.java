package com.questionoverflow.qof.web.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {

    private String title;
    private String cardContent;
    private String content;

    @Builder
    public PostsUpdateRequestDto(String title, String cardContent, String content){
        this.title = title;
        this.cardContent = cardContent;
        this.content = content;
    }
}
