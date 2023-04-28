package com.ontrustserver.domain.post.dto.response;

import lombok.Builder;

public record PostEdit(
        String title,
        String contents
) {
    @Builder
    public PostEdit {}
}
