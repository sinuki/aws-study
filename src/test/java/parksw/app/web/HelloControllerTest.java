package parksw.app.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HelloController.class)
class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    void helloDto가_리턴된다() throws Exception {
        String hello = "hello";
        int amount = 1_000;

        mvc.perform(
                get("/hello/dto")
                    .param("name", hello)
                    .param("amount", String.valueOf(amount))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(hello)))
            .andExpect(jsonPath("$.amount", is(amount)));
    }
}