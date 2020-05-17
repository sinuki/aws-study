package parksw.app.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import parksw.app.domain.posts.Posts;
import parksw.app.domain.posts.PostsRepository;
import parksw.app.web.dto.PostsResponseDto;
import parksw.app.web.dto.PostsSaveRequestDto;
import parksw.app.web.dto.PostsUpdateRequestDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * PostsApiControllerTest
 * author: sinuki
 * createdAt: 2020/05/01
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    void Posts_등록된다() {
        String title = "title";
        String content = "content";
        String author = "parksw";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
        String url = String.join("", "http://localhost:", String.valueOf(port), "/api/v1/posts");

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Posts> all = postsRepository.findAll();
        Posts post = all.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getAuthor()).isEqualTo(author);
    }

    @Test
    void Posts_수정된다() {
        Posts savedPost = postsRepository.save(Posts.builder().title("title").content("content").author("parksw").build());
        Long updateId = savedPost.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";
        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();
        String url = String.join("", "http://localhost:", String.valueOf(port), "/api/v1/posts/", String.valueOf(updateId));
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Posts> all = postsRepository.findAll();
        Posts post = all.get(0);
        assertThat(post.getTitle()).isEqualTo(expectedTitle);
        assertThat(post.getContent()).isEqualTo(expectedContent);
    }

    @Test
    void Posts_조회된다() {
        Posts savedPost = postsRepository.save(Posts.builder().title("title").content("content").author("parksw").build());
        Long showableId = savedPost.getId();
        String url = String.join("", "http://localhost:", String.valueOf(port), "/api/v1/posts/", String.valueOf(showableId));

        ResponseEntity<PostsResponseDto> responseEntity = restTemplate.getForEntity(url, PostsResponseDto.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        PostsResponseDto body = responseEntity.getBody();
        assertThat(body.getId()).isEqualTo(savedPost.getId());
        assertThat(body.getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(body.getContent()).isEqualTo(savedPost.getContent());
        assertThat(body.getAuthor()).isEqualTo(savedPost.getAuthor());
    }

    @Test
    void Posts_삭제된다() {
        Posts savedPost = postsRepository.save(Posts.builder().title("title").content("content").author("parksw").build());
        Long deleteId = savedPost.getId();
        String url = String.join("", "http://localhost:", String.valueOf(port), "/api/v1/posts/", String.valueOf(deleteId));

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(deleteId);
        assertThat(postsRepository.findById(deleteId).isEmpty()).isTrue();
    }
}