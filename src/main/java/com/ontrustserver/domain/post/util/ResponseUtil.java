package com.ontrustserver.domain.post.util;

import com.ontrustserver.domain.model.Post;
import com.ontrustserver.domain.post.dto.response.PostResponse;

public class ResponseUtil {
    public static PostResponse of(Post post){
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .createdDate(post.getCreatedDate())
                .updatedDate(post.getUpdatedDate())
                .build();
    }
}
