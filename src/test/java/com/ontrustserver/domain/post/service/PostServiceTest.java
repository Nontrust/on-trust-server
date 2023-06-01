package com.ontrustserver.domain.post.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontrustserver.domain.model.Post;
import com.ontrustserver.domain.post.dao.PostRepository;
import com.ontrustserver.domain.post.dto.request.PostRequest;
import com.ontrustserver.domain.post.dto.response.PostEdit;
import com.ontrustserver.domain.post.dto.response.PostResponse;
import com.ontrustserver.domain.post.exception.sub.PostNotFound;
import com.ontrustserver.global.common.dto.response.PagingRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        PostResponse post = postService.postSave(postRequest);
        long afterCount = postRepository.count();

        Instant expectedTime = Instant.now();
        Instant lowerInstant = expectedTime.minus(1, ChronoUnit.SECONDS);
        Instant upperInstant = expectedTime.plus(1, ChronoUnit.SECONDS);
        Instant createDateInstance = post.createdDate().toInstant();
        Instant updateDateInstance = post.updatedDate().toInstant();

        //then
        assertEquals(1, afterCount - beforeCount );
        assertEquals(post.title(), "test title");
        assertEquals(post.contents(), "test contents");

        assertTrue(updateDateInstance.isAfter(lowerInstant));
        assertTrue(updateDateInstance.isBefore(upperInstant));

        assertTrue(createDateInstance.isAfter(lowerInstant));
        assertTrue(createDateInstance.isBefore(upperInstant));
    }

    @Test
    @DisplayName("글 1개 조회")
    void getPostService() {
        //given
        Post requestPost = Post.builder().title("글 1").contents("컨텐츠 1").build();
        postRepository.save(requestPost);
        //when
        PostResponse responsePost = postService.getPostById(requestPost.getId());

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
    @DisplayName("글, 제목 Update")
    void updatePost() {
        // given
        String updateTitle = "업데이트 된 글 제목";
        String updateContents = "업데이트 된 컨텐츠";
        Post post = postRepository.fetchAnyOne();

        PostEdit postEdit = PostEdit.builder()
                .title(updateTitle)
                .contents(updateContents)
                .build();
        // when
        PostResponse editedPostResponse = postService.updatePostById(post.getId(), postEdit);
        Post editedPost = postRepository.findById(editedPostResponse.id())
                .orElseThrow(RuntimeException::new);

        Instant expectedTime = Instant.now();
        Instant lowerUpdateInstant = expectedTime.minus(1, ChronoUnit.SECONDS);
        Instant upperUpdateInstant = expectedTime.plus(1, ChronoUnit.SECONDS);

        Instant beforeCreateTime = post.getCreatedDate().toInstant();
        Instant lowerCreateInstant = beforeCreateTime.minus(1, ChronoUnit.SECONDS);
        Instant upperCreateInstant = beforeCreateTime.plus(1, ChronoUnit.SECONDS);



        Instant createDateInstance = editedPost.getCreatedDate().toInstant();
        Instant updateDateInstance = editedPost.getUpdatedDate().toInstant();

        // then
        assertEquals(editedPostResponse.title(), updateTitle);
        assertEquals(editedPostResponse.contents(), updateContents);

        assertEquals(editedPost.getTitle(), updateTitle);
        assertEquals(editedPost.getContents(), updateContents);

        //check create time
        assertTrue(updateDateInstance.isAfter(lowerUpdateInstant));
        assertTrue(updateDateInstance.isBefore(upperUpdateInstant));

        assertTrue(createDateInstance.isAfter(lowerCreateInstant));
        assertTrue(createDateInstance.isBefore(upperCreateInstant));

    }
    @Test
    @DisplayName("제목만 Update")
    void updatePostTitle() {
        // given
        String updateTitle = "업데이트 된 글 제목";
        Post post = postRepository.fetchAnyOne();

        PostEdit postEditOnlyTitle = PostEdit.builder()
                .title(updateTitle)
//                .contents(updateContents)
                .build();

        // when
        PostResponse editedPostTitle = postService.updatePostById(post.getId(), postEditOnlyTitle);
        // then
        assertEquals(editedPostTitle.title(), updateTitle);
        assertEquals(editedPostTitle.contents(), post.getContents());

    }
    @Test
    @DisplayName("컨텐츠만 Update")
    void updatePostContents() {
        // given
        String updateContents = "업데이트 된 컨텐츠";
        Post post = postRepository.fetchAnyOne();

        PostEdit postEditOnlyContents = PostEdit.builder()
//                .title(updateTitle)
                .contents(updateContents)
                .build();

        // when
        PostResponse editedPostContents = postService.updatePostById(post.getId(), postEditOnlyContents);
        // then
        assertEquals(editedPostContents.title(), post.getTitle());
        assertEquals(editedPostContents.contents(), updateContents);
    }

    @Test
    @DisplayName("게시글 삭제")
    void deletePostTest() {
        // given
        Post post = postRepository.fetchAnyOne();
        long beforeCount = postRepository.count();

        // when
        postService.deletePostById(post.getId());
        long afterCount = postRepository.count();

        // then
        assertEquals(beforeCount-afterCount, 1);
        assertFalse(postRepository.findById(post.getId()).isPresent());

    }

    @Test
    @DisplayName("존재하지 않는 글 조회 exception Test")
    void postNotFoundTest() {
        //given
        long wrongId = Long.MIN_VALUE;

        // expect
        PostNotFound getPostException
                = assertThrows(PostNotFound.class, () -> postService.getPostById(wrongId));
        PostNotFound deletePostException
                = assertThrows(PostNotFound.class, () -> postService.deletePostById(wrongId));
        PostNotFound updatePostException
                = assertThrows(PostNotFound.class, () -> postService.updatePostById(wrongId, PostEdit.builder().build()));

        // then
        assertTrue(getPostException.getMessage().contains("존재하지 않는 글"));
        assertTrue(deletePostException.getMessage().contains("존재하지 않는 글"));
        assertTrue(updatePostException.getMessage().contains("존재하지 않는 글"));
    }
}