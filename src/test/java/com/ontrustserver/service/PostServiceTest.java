package com.ontrustserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontrustserver.domain.Post;
import com.ontrustserver.repository.PostRepository;
import com.ontrustserver.request.PostRequest;
import com.ontrustserver.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostServiceTest {
    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ObjectMapper objectMapper;


    @BeforeEach
    void deleteAll() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void setPostService() {
        //given
        PostRequest postRequest = PostRequest.builder().title("test title").contents("test contents").build();
        //when
        PostResponse post = postService.post(postRequest);

        //then
        assertEquals(1, postRepository.count());
        assertEquals(post.title(), "test title");
        assertEquals(post.contents(), "test contents");
    }

    @Test
    @DisplayName("글 1개 조회")
    void getPostService() {
        //given
        Post requestPost = Post.builder().title("글 1").contents("컨텐츠 1").build();
        postRepository.save(requestPost);
        //when
        PostResponse responsePost = postService.get(requestPost.getId());

        //then
        assertNotNull(responsePost);
        assertEquals(responsePost.title(), "글 1");
        assertEquals(responsePost.contents(), "컨텐츠 1");
    }
    @Test
    @DisplayName("글 조회")
    void getPostListService() {
        //given
        List<Post> posts = IntStream.rangeClosed(1, 30)
                .mapToObj(r ->
                        Post.builder()
                                .title("글" + r)
                                .contents("컨텐츠" + r)
                                .build()
                )
                .toList();
        int size = 10;
        postRepository.saveAll(posts);
        //when
        List<PostResponse> responsePost = postService.getPostList(1, size, "asc");

        //then
        assertEquals(responsePost.size(), size);
    }

}