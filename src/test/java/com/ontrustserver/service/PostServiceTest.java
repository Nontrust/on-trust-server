package com.ontrustserver.service;

import com.ontrustserver.domain.Post;
import com.ontrustserver.repository.PostRepository;
import com.ontrustserver.request.PostCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostServiceTest {
    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;


    @BeforeEach
    void deleteAll(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void setPostService(){
        //given
        PostCreate postCreate = PostCreate.builder().title("test title").contents("test contents").build();
        //when
        Post post = postService.post(postCreate);

        //then
        assertEquals(1, postRepository.count());
        assertEquals(post.getTitle(), "test title");
        assertEquals(post.getContents(), "test contents");
    }

    @Test
    @DisplayName("글 1개 조회")
    void getPostService(){
        //given
        Post requestPost = Post.builder().title("글 1").contents("컨텐츠 1").build();
        postRepository.save(requestPost);
        //when
        Post responsePost = postService.get(requestPost.getId());

        //then
        assertNotNull(responsePost);
        assertEquals(responsePost.getTitle(), "글 1");
        assertEquals(responsePost.getContents(), "컨텐츠 1");
    }

}