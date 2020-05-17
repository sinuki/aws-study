package parksw.app.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import parksw.app.service.posts.PostsService;
import parksw.app.web.dto.PostsResponseDto;

/**
 * IndexController
 * author: sinuki
 * createdAt: 2020/05/02
 **/
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }

    @GetMapping("posts/save")
    public String postsSave() {
        return "posts/save";
    }

    @GetMapping("posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = new PostsResponseDto(postsService.findById(id));
        model.addAttribute("post", dto);
        return "posts/update";
    }
}
