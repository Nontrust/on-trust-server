package com.ontrustserver.domain.post.exception.sub;

import com.ontrustserver.domain.post.exception.PostDomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class PostNotFound extends PostDomainException {

    @Override
    public HttpStatus statusCode() {
        return HttpStatus.NOT_FOUND;
    }

    private static final String MESSAGE = "존재하지 않는 글입니다.";
    public PostNotFound() {
        super(MESSAGE);
        log.warn(MESSAGE);
    }
}
