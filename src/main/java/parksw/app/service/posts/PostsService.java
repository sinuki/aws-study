package parksw.app.service.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parksw.app.domain.posts.Posts;
import parksw.app.domain.posts.PostsRepository;
import parksw.app.web.dto.PostsSaveRequestDto;
import parksw.app.web.dto.PostsUpdateRequestDto;

/**
 * PostsService
 * author: sinuki
 * createdAt: 2020/05/01
 **/
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = findById(id);
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public Posts findById(Long id) {
        return postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
    }
}
