package com.ontrustserver.response;

import lombok.Builder;

public record PostEdit(
        String title,
        String contents
) {
    @Builder
    public PostEdit {}
}
