package parksw.app.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import parksw.app.domain.posts.Posts;
import parksw.app.domain.posts.PostsRepository;
import parksw.app.web.dto.PostsSaveRequestDto;
import parksw.app.web.dto.PostsUpdateRequestDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * PostsApiControllerTest
 * author: sinuki
 * createdAt: 2020/05/01
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @BeforeEach
    void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    void Posts_등록된다() throws Exception {
        String title = "title";
        String content = "content";
        String author = "parksw";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        mvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Posts> all = postsRepository.findAll();
        Posts post = all.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getAuthor()).isEqualTo(author);
    }

    @Test
    @WithMockUser(roles = "USER")
    void Posts_수정된다() throws Exception {
        Posts savedPost = postsRepository.save(Posts.builder().title("title").content("content").author("parksw").build());
        Long updateId = savedPost.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";
        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        mvc.perform(put("/api/v1/posts/{id}", updateId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Posts> all = postsRepository.findAll();
        Posts post = all.get(0);
        assertThat(post.getTitle()).isEqualTo(expectedTitle);
        assertThat(post.getContent()).isEqualTo(expectedContent);
    }

    @Test
    @WithMockUser(roles = "USER")
    void Posts_조회된다() throws Exception {
        Posts savedPost = postsRepository.save(Posts.builder().title("title").content("content").author("parksw").build());
        Long showableId = savedPost.getId();

        mvc.perform(get("/api/v1/posts/{id}", showableId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedPost.getId().intValue())))
                .andExpect(jsonPath("$.title", is(savedPost.getTitle())))
                .andExpect(jsonPath("$.content", is(savedPost.getContent())))
                .andExpect(jsonPath("$.author", is(savedPost.getAuthor())));
    }

    @Test
    @WithMockUser(roles = "USER")
    void Posts_삭제된다() throws Exception {
        Posts savedPost = postsRepository.save(Posts.builder().title("title").content("content").author("parksw").build());
        Long deleteId = savedPost.getId();

        mvc.perform(delete("/api/v1/posts/{id}", deleteId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(is(deleteId.toString())));
        assertThat(postsRepository.findById(deleteId).isEmpty()).isTrue();
    }
}