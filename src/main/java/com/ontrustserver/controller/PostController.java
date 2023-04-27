package com.ontrustserver.controller;

import com.ontrustserver.request.PagingRequest;
import com.ontrustserver.request.PostRequest;
import com.ontrustserver.response.PostResponse;
import com.ontrustserver.service.PostService;
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

    @PostMapping("/post")
    public PostResponse post(@RequestBody @Valid PostRequest postRequest){
        return postService.post(postRequest);
    }
    @GetMapping("/post/{postId}")
    public PostResponse getPost(@PathVariable(name = "postId") Long id){
        return postService.get(id);
    }

    @GetMapping("/post")
    public List<PostResponse> getPostList(@Valid PagingRequest pagingRequest){
        return postService.getPostList(pagingRequest);
    }
}
