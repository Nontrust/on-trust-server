package com.ontrustserver.controller;



import com.ontrustserver.request.PostCreate;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class PostController {
    @PostMapping("/post")
    public Map<String, String> post(@RequestBody @Valid PostCreate params){
        HashMap<String, String> result = new HashMap<>();

        result.put("title", params.title());
        result.put("contents", params.contents());

        return result;
    }
}
