package parksw.app.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * PostsRepositoryTest
 * author: sinuki
 * createdAt: 2020/04/30
 **/
@SpringBootTest
class PostsRepositoryTest {

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    void 게시글저장_불러오기() {
        String title = "테스트 게시글";
        String content = "테스트 본문";
        String author = "parksw";
        postsRepository.save(Posts.builder().title(title).content(content).author(author).build());

        List<Posts> posts = postsRepository.findAll();

        Posts post = posts.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getAuthor()).isEqualTo(author);
    }

    @Test
    void BaseTimeEntity_등록() {
        LocalDateTime now = LocalDateTime.of(2020, 5, 1, 0, 0, 0);
        postsRepository.save(Posts.builder().title("title").content("content").author("parksw").build());

        List<Posts> posts = postsRepository.findAll();

        Posts post = posts.get(0);
        System.out.println("createdDate=" + post.getCreatedDate() + ", lastModifiedDate=" + post.getModifiedDate());
        assertThat(post.getCreatedDate()).isAfter(now);
        assertThat(post.getModifiedDate()).isAfter(now);
    }
}