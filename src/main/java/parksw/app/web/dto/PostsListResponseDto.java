package parksw.app.web.dto;

import lombok.Getter;
import parksw.app.domain.posts.Posts;

import java.time.LocalDateTime;

/**
 * PostsListResponseDto
 * author: sinuki
 * createdAt: 2020/05/03
 **/
@Getter
public class PostsListResponseDto {

    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
    }
}
