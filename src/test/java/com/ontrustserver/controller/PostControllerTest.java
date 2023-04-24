package com.ontrustserver.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontrustserver.domain.post.Post;
import com.ontrustserver.repository.PostRepository;
import com.ontrustserver.request.PagingRequest;
import com.ontrustserver.request.PostRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Slf4j
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanRepository() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("Post 테스트")
    void test() throws Exception {
        //given

        PostRequest request = PostRequest.builder().title("test title").contents("test contents").build();
        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc
                .perform(post("/post", request)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contents", is("test contents")))
                .andExpect(jsonPath("$.title", is("test title")))
                .andDo(print());
    }

    @Test
    @DisplayName("Post validate 테스트")
    void validateTest() throws Exception {
        PostRequest request = PostRequest.builder().title("").contents("").build();
        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc
                .perform(post("/post", request)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is("400")))
                .andExpect(jsonPath("$.message", is("잘못된 요청입니다.")))
                .andExpect(jsonPath("$.validation.title", is("title은 필수입니다.")))
                .andDo(print());
    }

    @Test
    @DisplayName("Post 저장 테스트")
    void repositoryTest() throws Exception {
        PostRequest request = PostRequest.builder().title("test title").contents("test contents").build();
        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc
                .perform(post("/post", request)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contents", is("test contents")))
                .andExpect(jsonPath("$.title", is("test title")))
                .andDo(print());
        // then
        assertEquals(postRepository.count(), 1);
        Post post = postRepository.findAll().get(0);

        assertEquals(post.getTitle(), "test title");
        assertEquals(post.getContents(), "test contents");
    }

    @Test
    @DisplayName("글 1개 조회 test")
    void getByIdTest() throws Exception {
        //given
        Post post = Post.builder()
                .title("글 1")
                .contents("컨텐츠 1")
                .build();
        postRepository.save(post);

        //expect
        mockMvc
                .perform(get("/post/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("글 1")))
                .andExpect(jsonPath("$.contents", is("컨텐츠 1")))
                .andDo(print());
    }

    @Test
    @DisplayName("글 1개 조회 test")
    void getListTest() throws Exception {
        //given
        List<Post> posts = IntStream.rangeClosed(1, 30)
                .mapToObj(r ->
                        Post.builder().title("글" + r).contents("컨텐츠" + r).build()
                ).toList();
        postRepository.saveAll(posts);

        PagingRequest request = PagingRequest.builder()
                .page(1)
                .size(10)
                .order("asc")
                .build();

        //expect
        MvcResult mvcResult = mockMvc
                .perform(get("/post", request)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andDo(print())
                .andReturn();

        // then
        String responseJson = mvcResult.getResponse().getContentAsString();
        List<Post> postList = objectMapper.readValue(responseJson, new TypeReference<List<Post>>() {});
        assertEquals(postList.size(), 10);
    }

}