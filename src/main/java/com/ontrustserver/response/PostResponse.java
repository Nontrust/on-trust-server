package com.ontrustserver.response;

import com.ontrustserver.domain.Post;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<PostResponse> listToResponse(List<Post> posts){
        return posts.stream().map(post ->
            PostResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .contents(post.getContents())
                    .build()
        ).collect(Collectors.toList());
    }
}
