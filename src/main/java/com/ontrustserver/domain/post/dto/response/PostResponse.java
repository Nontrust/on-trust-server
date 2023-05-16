package com.ontrustserver.domain.post.dto.response;

import lombok.Builder;

import java.time.ZonedDateTime;

public record PostResponse(
        Long id,
        String title,
        String contents,
        ZonedDateTime createDate,
        ZonedDateTime updateDate
) {
    @Builder
    public PostResponse {
    }
}
