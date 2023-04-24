package com.ontrustserver.response;

import com.ontrustserver.domain.post.Post;
import lombok.Builder;

public record PostResponse(
        Long id,
        String title,
        String contents
) {
    @Builder
    public PostResponse(Long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public static PostResponse postToResponse(Post post){
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .build();
    }
}
