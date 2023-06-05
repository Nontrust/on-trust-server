package com.ontrustserver.domain.post.controller;

import com.ontrustserver.domain.post.dto.request.PostRequest;
import com.ontrustserver.domain.post.dto.response.PostEdit;
import com.ontrustserver.domain.post.dto.response.PostResponse;
import com.ontrustserver.domain.post.service.PostService;
import com.ontrustserver.global.aspect.badword.BadWord;
import com.ontrustserver.global.auth.domain.AuthSession;
import com.ontrustserver.global.common.dto.response.PagingRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RequestMapping("/post")
@RestController
public class PostController {
    private final PostService postService;

    @BadWord
    @PostMapping
    public PostResponse post(@RequestBody @Valid PostRequest postRequest, AuthSession authSession) {
        return postService.postSave(postRequest);
    }

    @GetMapping("/{postId}")
    public PostResponse getPost(@PathVariable(name = "postId") Long id){
        return postService.getPostById(id);
    }

    @GetMapping
    public List<PostResponse> getPostList(@Valid PagingRequest pagingRequest){
        return postService.getPostList(pagingRequest);
    }

    @BadWord
    @PutMapping("/{postId}")
    public PostResponse updatePost(@PathVariable(name = "postId") Long id, @RequestBody PostEdit postEdit, AuthSession authSession) {
        return postService.updatePostById(id, postEdit);
    }

    @DeleteMapping("/{postId}")
    public PostResponse deletePostById(@PathVariable(name = "postId") Long id, AuthSession authSession){
        return postService.deletePostById(id);
    }

}
