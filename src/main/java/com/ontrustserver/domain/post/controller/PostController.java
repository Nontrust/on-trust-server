package com.ontrustserver.domain.post.controller;

import com.ontrustserver.domain.post.dto.request.PostRequest;
import com.ontrustserver.domain.post.dto.response.PostEdit;
import com.ontrustserver.domain.post.dto.response.PostResponse;
import com.ontrustserver.domain.post.service.PostService;
import com.ontrustserver.global.aspect.badword.BadWord;
import com.ontrustserver.global.common.request.PagingRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Slf4j
@RestController
public class PostController {
    private final PostService postService;


    @BadWord
    @PostMapping("/post")
    public PostResponse post(@RequestBody @Valid PostRequest postRequest) {
        return postService.postSave(postRequest);
    }

    @GetMapping("/post/{postId}")
    public PostResponse getPost(@PathVariable(name = "postId") Long id){
        return postService.getPostById(id);
    }

    @GetMapping("/post")
    public List<PostResponse> getPostList(@Valid PagingRequest pagingRequest){
        return postService.getPostList(pagingRequest);
    }

    @BadWord
    @PutMapping("/post/{postId}")
    public PostResponse updatePost(@PathVariable(name = "postId") Long id, @RequestBody PostEdit postEdit) {
        return postService.updatePostById(id, postEdit);
    }

    @DeleteMapping("/post/{postId}")
    public PostResponse deletePostById(@PathVariable(name = "postId") Long id){
        return postService.deletePostById(id);
    }

}
