package com.ontrustserver.controller;


import com.ontrustserver.request.PostCreate;
import com.ontrustserver.service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Slf4j
@RestController
public class PostController {
    private final PostService postService;

    @PostMapping("/post")
    public Map<String, String> post(@RequestBody @Valid PostCreate params){
        HashMap<String, String> result = new HashMap<>();
        result.put("title", params.title());
        result.put("contents", params.contents());

        postService.post(params);

        return result;
    }
}
