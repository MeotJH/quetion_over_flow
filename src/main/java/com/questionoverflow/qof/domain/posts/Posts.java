package com.questionoverflow.qof.domain.posts;

import com.questionoverflow.qof.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 500)
    private String cardContent;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Column(columnDefinition = "integer default 0")
    private Long viewCnt = 0L;

    @Builder
    public Posts(String title, String cardContent, String content, String author){
        this.title = title;
        this.cardContent = cardContent;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void updateViewCnt(Long viewCnt){
        this.viewCnt = viewCnt;
    }

}
