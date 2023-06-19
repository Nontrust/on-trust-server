package com.ontrustserver.domain.badword.controller;

import com.ontrustserver.domain.badword.dto.BadWordRequest;
import com.ontrustserver.domain.badword.service.BadWordService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RequestMapping("/bad-word")
@RestController
public class BadWordController {
    private final BadWordService badWordService;
    @PostMapping
    public void badWord(@RequestBody @Valid BadWordRequest badWordRequest) {
        badWordService.setBadWord(badWordRequest);
    }
}
