package com.questionoverflow.qof.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.questionoverflow.qof.common.exception.NoPostException;
import com.questionoverflow.qof.domain.posts.Posts;
import com.questionoverflow.qof.domain.posts.PostsRepository;
import com.questionoverflow.qof.web.dto.post.PostsSaveRequestDto;
import com.questionoverflow.qof.web.dto.post.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostApiControllerTest {


    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule());

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PostsRepository postsRepository;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    @DisplayName("글이 등록되어야 한다.")
    @Transactional
    @WithMockUser(roles="USER")
    void save() throws Exception {

        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto
                                                    .builder()
                                                    .title(title)
                                                    .content(content)
                                                    .author("author")
                                                    .build();
        String url = new StringBuilder().append("http://localhost:").append(port).append("/api/v1/posts").toString();

        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)));

        //then

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);

    }

    @Test
    @DisplayName("제목과 내용이 업데이트 되어야 한다.")
    @WithMockUser(roles="USER")
    void update() throws Exception{

        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto
                                            .builder()
                                            .title(expectedTitle)
                                            .content(expectedContent)
                                            .build();

        String url = new StringBuilder().append("http://localhost:").append(port).append("/api/v1/posts/").append(updateId).toString();

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto))
        );

        //then

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

    }

    @Test
    @DisplayName("Posts의 모든 값을 가져와야 한다.")
    @WithMockUser(roles="USER")
    void findAllTest() throws Exception {
        //given
        Posts savedPost1 = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Posts savedPost2 = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Posts savedPost3 = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        List<Posts> all = postsRepository.findAll();

        String url = new StringBuilder().append("http://localhost:").append(port).append("/api/v1/posts").toString();

        //when
        MvcResult mvcResult = mvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        List responseBody = MAPPER.readValue(mvcResult.getResponse().getContentAsString(), List.class);

        //then
        assertThat(responseBody.size()).isEqualTo(all.size());

    }

    @Test
    @DisplayName("posts 의 id를 받아 삭제해야 한다.")
    @WithMockUser(roles="USER")
    void deleteTest() throws Exception {
        //given
        Posts savedPost = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Posts entity = postsRepository.findById(savedPost.getId()).orElseThrow(() -> new NoPostException(savedPost.getId()));
        postsRepository.flush();
        String url = new StringBuilder().append("http://localhost:").append(port).append("/api/v1/posts/").append(entity.getId()).toString();

        //when
        ResultActions perform = mvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());
        assertThrows(NoSuchElementException.class,() -> {
            postsRepository.findById(entity.getId()).get();
        });


    }


}