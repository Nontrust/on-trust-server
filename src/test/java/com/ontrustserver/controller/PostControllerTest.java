package com.ontrustserver.controller;

import com.ontrustserver.domain.Post;
import com.ontrustserver.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void cleanRepository() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("포스트")
    void test() throws Exception {
        // expected
        mockMvc
                .perform(post("/post")
                        .contentType(APPLICATION_JSON)
                        .content("{\"title\":\"test title\",\"contents\":\"test contents\"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contents", is("test contents")))
                .andExpect(jsonPath("$.title", is("test title")))
                .andDo(print());
    }

    @Test
    @DisplayName("validate 테스트")
    void validateTest() throws Exception {
        // expected
        mockMvc
                .perform(post("/post")
                        .contentType(APPLICATION_JSON)
                        .content("{\"title\":\"\",\"contents\":\"test contents\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is("400")))
                .andExpect(jsonPath("$.message", is("잘못된 요청입니다.")))
                .andExpect(jsonPath("$.validation.title", is("title은 필수입니다.")))
                .andDo(print());
    }

    @Test
    @DisplayName("Post repository 테스트")
    void repositoryTest() throws Exception {
        // when
        mockMvc
                .perform(post("/post")
                        .contentType(APPLICATION_JSON)
                        .content("{\"title\":\"test title\",\"contents\":\"test contents\"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contents", is("test contents")))
                .andExpect(jsonPath("$.title", is("test title")))
                .andDo(print());
        // then
        Assertions.assertEquals(postRepository.count(), 1);
        Post post = postRepository.findAll().get(0);

        Assertions.assertEquals(post.getTitle(), "test title");
        Assertions.assertEquals(post.getContents(), "test contents");

    }
}