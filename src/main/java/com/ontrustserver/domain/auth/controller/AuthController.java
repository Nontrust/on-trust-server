package com.ontrustserver.domain.auth.controller;

import com.ontrustserver.domain.auth.dto.request.LoginRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {
    @PostMapping("login")
    public void login(@RequestBody LoginRequest loginRequest){
        log.info("{}",loginRequest);
    }
}
