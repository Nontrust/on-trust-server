package com.ontrustserver.domain.post.dto.response;

import lombok.Builder;

import java.time.ZonedDateTime;

public record PostResponse(
        Long id,
        String title,
        String contents,
        ZonedDateTime createdDate,
        ZonedDateTime updatedDate
) {
    @Builder
    public PostResponse {
    }
}
