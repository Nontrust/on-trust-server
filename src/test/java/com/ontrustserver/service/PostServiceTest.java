package com.ontrustserver.service;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
class PostServiceTest {
    @Autowired
    ApplicationContext context;
    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ObjectMapper objectMapper;


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
    void deleteAll() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void setPostService() {
        //given
        PostRequest postRequest = PostRequest.builder().title("test title").contents("test contents").build();
        //when
        long beforeCount = postRepository.count();
        PostResponse post = postService.post(postRequest);
        long afterCount = postRepository.count();

        //then
        assertEquals(1, afterCount - beforeCount );
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
        PostResponse responsePost = postService.getById(requestPost.getId());

        //then
        assertNotNull(responsePost);
        assertEquals(responsePost.title(), "글 1");
        assertEquals(responsePost.contents(), "컨텐츠 1");
    }
    @Test
    @DisplayName("글 30개 중 10개 조회")
    void getPostListService() {
        int size = 10;
        //when
        PagingRequest asc = PagingRequest.builder().page(1).size(size)
                .order("asc").build();
        PagingRequest desc = PagingRequest.builder().page(1).size(size)
                .order("desc").build();

        List<PostResponse> responsePostOrderByAsc = postService.getPostList(asc);
        List<PostResponse> responsePostOrderByDesc = postService.getPostList(desc);
        //then

        assertEquals(responsePostOrderByAsc.size(), size);
        assertEquals(responsePostOrderByDesc.size(), size);

        assertThat(responsePostOrderByAsc.get(0).id())
                .isLessThan(responsePostOrderByAsc.get(1).id());

        assertThat(responsePostOrderByDesc.get(0).id())
                .isGreaterThan(responsePostOrderByDesc.get(1).id());

    }

    @Test
    @DisplayName("글 제목 Update")
    void updatePostTitle() {
        // given
        String updateTitle = "업데이트 된 글 제목";
        String updateContents = "업데이트 된 타이틀";
        Post post = postRepository.fetchAnyOne();
        PostEdit postEdit = PostEdit.builder()
                .title(updateTitle)
                .contents(updateContents)
                .build();
        // when
        PostResponse editedPost = postService.updatePostById(post.getId(), postEdit);
        // then
        assertEquals(editedPost.title(), updateTitle);
        assertEquals(editedPost.contents(), updateContents);
    }

}