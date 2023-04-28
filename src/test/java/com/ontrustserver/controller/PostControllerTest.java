package com.ontrustserver.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontrustserver.domain.post.Post;
import com.ontrustserver.repository.PostRepository;
import com.ontrustserver.request.PagingRequest;
import com.ontrustserver.request.PostRequest;
import com.ontrustserver.response.PostEdit;
import com.ontrustserver.response.PostResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void setPost(){
        // given
        List<Post> posts = IntStream.rangeClosed(1, 30)
                .mapToObj(r ->Post.builder()
                        .title("글" + r)
                        .contents("컨텐츠" + r)
                        .build()
                ).toList();
        postRepository.saveAll(posts);
    }

    @AfterEach
    void cleanRepository() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("Post Json데이터 테스트")
    void postTest() throws Exception {
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
        // given
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
    @DisplayName("Post 1개 저장 테스트")
    void repositoryTest() throws Exception {
        // given
        String title = "test title";
        String contents = "test contents";

        PostRequest request = PostRequest.builder().title(title).contents(contents).build();
        String json = objectMapper.writeValueAsString(request);
        long beforeCount = postRepository.count();

        // expected
        MvcResult result = mockMvc
                .perform(post("/post", request)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(title)))
                .andExpect(jsonPath("$.contents", is(contents)))
                .andDo(print())
                .andReturn();
        // then
        String responseJson = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseJson);
        // postRepository에 1행 추가
        assertEquals(postRepository.count()-beforeCount, 1);
        // json 경로에 정상 추가 확인
        assertEquals(jsonNode.get("title").asText("fallback"), title);
        assertEquals(jsonNode.get("contents").asText("fallback"), contents);
    }

    @Test
    @DisplayName("글 1개 조회 test")
    void getByIdTest() throws Exception {
        //given
        String title = "글 1";
        String contents = "컨텐츠 1";
        Post post = Post.builder()
                .title(title)
                .contents(contents)
                .build();
        postRepository.save(post);

        //expect
        mockMvc
                .perform(get("/post/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(title)))
                .andExpect(jsonPath("$.contents", is(contents)))
                .andDo(print());
    }

    @Test
    @DisplayName("글 1개 조회 test")
    void getListTest() throws Exception {
        // given
        PagingRequest request = PagingRequest.builder()
                .page(1)
                .size(10)
                .order("asc")
                .build();

        // expect
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
        List<PostResponse> postList = objectMapper.readValue(responseJson, new TypeReference<List<PostResponse>>() {});
        assertEquals(postList.size(), 10);
    }
    @Test
    @DisplayName("글 수정 테스트")
    void updateTest() throws Exception {
        // given
        String title = "변경된 타이틀";
        String contents = "변경된 컨텐츠";
        Post post = postRepository.fetchAnyOne();
        PostEdit request = PostEdit.builder()
                .title(title)
                .contents(contents)
                .build();
        String json = objectMapper.writeValueAsString(request);
        // expect
        MvcResult mvcResult = mockMvc
                .perform(put("/post/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(title)))
                .andExpect(jsonPath("$.contents", is(contents)))
                .andDo(print())
                .andReturn();

    }

}