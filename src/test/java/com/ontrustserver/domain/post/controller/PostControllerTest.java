package com.ontrustserver.domain.post.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontrustserver.domain.model.Post;
import com.ontrustserver.domain.post.dao.PostRepository;
import com.ontrustserver.domain.post.dto.request.PostRequest;
import com.ontrustserver.domain.post.dto.response.PostEdit;
import com.ontrustserver.domain.post.dto.response.PostResponse;
import com.ontrustserver.global.common.dto.response.PagingRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
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

        Instant expectedTime = Instant.now();
        Instant lowerInstant = expectedTime.minus(1, ChronoUnit.SECONDS);
        Instant upperInstant = expectedTime.plus(1, ChronoUnit.SECONDS);

        // expected
        MvcResult mvcResult = mockMvc
                .perform(post("/post", request)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contents", is("test contents")))
                .andExpect(jsonPath("$.title", is("test title")))
                .andDo(print())
                .andReturn();

        // then

        String contentAsString = mvcResult.getResponse().getContentAsString();
        PostResponse response = objectMapper.readValue(contentAsString, PostResponse.class);
        Instant createDateInstance = response.createdDate().toInstant();
        Instant updateDateInstance = response.updatedDate().toInstant();

        assertTrue(createDateInstance.isAfter(lowerInstant));
        assertTrue(createDateInstance.isBefore(upperInstant));

        assertTrue(updateDateInstance.isAfter(lowerInstant));
        assertTrue(updateDateInstance.isBefore(upperInstant));
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
                .andExpect(jsonPath("$.code", is(400)))
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

        Instant expectedTime = Instant.now();
        Instant lowerInstant = expectedTime.minus(1, ChronoUnit.SECONDS);
        Instant upperInstant = expectedTime.plus(1, ChronoUnit.SECONDS);

        //expect
        MvcResult mvcResult = mockMvc
                .perform(get("/post/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(title)))
                .andExpect(jsonPath("$.contents", is(contents)))
                .andDo(print())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        PostResponse response = objectMapper.readValue(contentAsString, PostResponse.class);
        Instant createDateInstance = response.createdDate().toInstant();
        Instant updateDateInstance = response.updatedDate().toInstant();

        assertTrue(createDateInstance.isAfter(lowerInstant));
        assertTrue(createDateInstance.isBefore(upperInstant));

        assertTrue(updateDateInstance.isAfter(lowerInstant));
        assertTrue(updateDateInstance.isBefore(upperInstant));
    }

    @Test
    @DisplayName("글 리스트 조회 test")
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
        List<PostResponse> postList = objectMapper.readValue(responseJson, new TypeReference<>() {});
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

        Instant expectedTime = Instant.now();
        Instant lowerInstant = expectedTime.minus(1, ChronoUnit.SECONDS);
        Instant upperInstant = expectedTime.plus(1, ChronoUnit.SECONDS);

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

        // then
        String contentAsString = mvcResult.getResponse().getContentAsString();
        PostResponse response = objectMapper.readValue(contentAsString, PostResponse.class);
        Instant updateDateInstance = response.updatedDate().toInstant();

        assertTrue(updateDateInstance.isAfter(lowerInstant));
        assertTrue(updateDateInstance.isBefore(upperInstant));
    }
    @Test
    @DisplayName("글 삭제 테스트")
    void deleteTest() throws Exception {
        // given
        Post post = postRepository.fetchAnyOne();
        long beforeCount = postRepository.count();

        // expect
        mockMvc
                .perform(delete("/post/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();


        //then
        Optional<Post> DeletedPost = postRepository.findById(post.getId());
        assertFalse(DeletedPost.isPresent());
        assertEquals(beforeCount-postRepository.count(), 1);
    }

    @Test
    @DisplayName("존재하지 않는 글 조회 exception Test")
    void postNotFoundTest() throws Exception {
        // given
        long wrongId = Long.MIN_VALUE;
        PostEdit blankRequest = PostEdit.builder().build();
        String blankJson = objectMapper.writeValueAsString(blankRequest);

        String resultMessage = "잘못된 요청입니다.";
        String resultValidation = "존재하지 않는 글입니다.";

        int status = HttpStatus.NOT_FOUND.value();

        // expect
        mockMvc
                .perform(get("/post/{postId}", wrongId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(status)))
                .andExpect(jsonPath("$.message", is(resultMessage)))
                .andExpect(jsonPath("$.validation.parameter", is(resultValidation)))
                .andDo(print())
                .andReturn();
        mockMvc
                .perform(delete("/post/{postId}", wrongId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(status)))
                .andExpect(jsonPath("$.message", is(resultMessage)))
                .andExpect(jsonPath("$.validation.parameter", is(resultValidation)))
                .andDo(print())
                .andReturn();
        mockMvc
                .perform(put("/post/{postId}", wrongId)
                        .contentType(APPLICATION_JSON)
                        .content(blankJson)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(status)))
                .andExpect(jsonPath("$.message", is(resultMessage)))
                .andExpect(jsonPath("$.validation.parameter", is(resultValidation)))
                .andDo(print())
                .andReturn();
    }
}