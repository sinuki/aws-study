package parksw.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import parksw.app.service.posts.PostsService;
import parksw.app.web.dto.PostsResponseDto;
import parksw.app.web.dto.PostsSaveRequestDto;
import parksw.app.web.dto.PostsUpdateRequestDto;

/**
 * PostsApiController
 * author: sinuki
 * createdAt: 2020/05/01
 **/
@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return new PostsResponseDto(postsService.findById(id));
    }
}
