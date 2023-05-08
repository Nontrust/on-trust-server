package com.ontrustserver.domain.post.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostNotFound extends RuntimeException{

    private static final String MESSAGE = "존재하지 않는 글입니다.";
    public PostNotFound() {
        super(MESSAGE);
        log.warn(MESSAGE);
    }
}
