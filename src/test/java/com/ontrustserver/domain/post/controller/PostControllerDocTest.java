package com.ontrustserver.domain.post.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontrustserver.domain.model.Post;
import com.ontrustserver.domain.post.dao.PostRepository;
import com.ontrustserver.domain.post.dto.request.PostRequest;
import com.ontrustserver.global.common.request.PagingRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.IntStream;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "domain.com", uriPort = 443)
public class PostControllerDocTest {

  @Autowired
  private PostRepository postRepository;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    List<Post> posts = IntStream.rangeClosed(1, 30)
        .mapToObj(r -> Post.builder()
            .title("글" + r)
            .contents("컨텐츠" + r)
            .build()
        ).toList();
    postRepository.saveAll(posts);
  }

  @Test
  @DisplayName("글 1개 조회")
  void findPostById() throws Exception {
    Post post = postRepository.fetchAnyOne();
    this.mockMvc.perform(get("/post/{postId}", post.getId())
            .accept(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("findPostById",
            pathParameters(
                parameterWithName("postId").description("게시글 ID")
            ),
            responseFields(
                fieldWithPath("id").description("게시글 ID"),
                fieldWithPath("title").description("글 제목"),
                fieldWithPath("contents").description("글 내용"),
                fieldWithPath("createdDate").description("UTC Time의 생성 일자"),
                fieldWithPath("updatedDate").description("UTC Time의 수정 일자")
            )
        ));
  }

  @Test
  @DisplayName("글 리스트 조회")
  void findPostListByPagingRequest() throws Exception {
    // given
    PagingRequest request = PagingRequest.builder()
        .page(1)
        .size(10)
        .order("asc")
        .build();
    // Todo: attributes (adoc문서에 포함이안되네요)
    // expect
    mockMvc
        .perform(get("/post", request)
            .contentType(APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("findPostListByPagingRequest",
                pathParameters(
                    parameterWithName("page")
                        .description("페이징 Number")
                        .attributes(
                            key("default").value("1"),
                            key("min").value("1")
                        ).optional(),
                    parameterWithName("size")
                        .description("페이징 Size")
                        .attributes(
                            key("default").value("1"),
                            key("min").value("1")
                        ).optional(),
                    parameterWithName("order")
                        .description("정렬 순서 [asc, desc]")
                        .attributes(
                            key("default").value("asc"),
                            key("allowedValues").value("asc,desc")
                        ).optional()
                )
        ));
  }

  @Test
  @DisplayName("Post 저장")
  void saveToPostRequest() throws Exception {
    // given
    String title = "test title";
    String contents = "test contents";

    PostRequest request = PostRequest.builder().title(title).contents(contents).build();
    String json = objectMapper.writeValueAsString(request);

    // expected
    mockMvc
        .perform(post("/post", request)
            .contentType(APPLICATION_JSON)
            .content(json)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("saveToPostRequest",
            requestFields(
                fieldWithPath("title").description("제목"),
                fieldWithPath("contents").description("내용")
            )
        ));

  }
}
