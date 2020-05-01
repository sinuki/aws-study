package parksw.app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import parksw.app.domain.posts.Posts;

/**
 * PostsSaveRequestDto
 * author: sinuki
 * createdAt: 2020/05/01
 **/
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostsSaveRequestDto {

    private String title;
    private String content;
    private String author;

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
