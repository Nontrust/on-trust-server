package com.ontrustserver.service;

import com.ontrustserver.domain.Post;
import com.ontrustserver.repository.PostRepository;
import com.ontrustserver.request.PostCreate;
import com.ontrustserver.response.PostResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository  postRepository;
    public PostResponse post(PostCreate postCreate) {
        Post post = Post.builder()
                .title(postCreate.title())
                .contents(postCreate.contents())
                .build();
        postRepository.save(post);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .build();
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .build();
    }
    }
}
