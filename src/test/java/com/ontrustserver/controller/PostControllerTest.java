package com.ontrustserver.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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
}