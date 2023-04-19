package com.ontrustserver.controller;

import com.ontrustserver.domain.Post;
import com.ontrustserver.request.PostCreate;
import com.ontrustserver.service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Slf4j
@RestController
public class PostController {
    private final PostService postService;

    @PostMapping("/post")
    public Post post(@RequestBody @Valid PostCreate postCreate){
        return postService.post(postCreate);
    }
    @GetMapping("/post/{postId}")
    public Post get(@PathVariable(name = "postId") Long id){
        return postService.get(id);
    }
}
