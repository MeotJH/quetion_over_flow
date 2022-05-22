package com.questionoverflow.qof.domain.posts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;



    @Test
    @DisplayName("게시글 저장 불러오기")
    void postsSaveTest(){
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문 입니다다다다다다다다다.";
        String author = "KJH";

        postsRepository.save(Posts
                .builder()
                .title(title)
                .content(content)
                .author(author)
                .build()
        );

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat( posts.getTitle() ).isEqualTo(title);
        assertThat( posts.getContent()).isEqualTo(content);
        assertThat( posts.getAuthor() ).isEqualTo( author );
    }

    @Test
    @DisplayName("베이스 엔티티가 등록되어 시간이 인서트되어야 한다.")
    void BaseTimeEntityTest(){
        //given
        LocalDateTime now = LocalDateTime.now();//LocalDateTime.of(2022,5,16,0,0,0);
        //LocalDateTime.now();
        postsRepository.save(Posts
                .builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>>>>>>>>>> createDate = " + posts.getCreatedDate() + " , modifiedDate = " + posts.getModifiedDate());

        assertThat(posts.getCreatedDate().getDayOfYear()).isEqualTo(now.getDayOfYear());
        assertThat(posts.getModifiedDate().getDayOfYear()).isEqualTo(now.getDayOfYear());
    }
}