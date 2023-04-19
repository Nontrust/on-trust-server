package com.ontrustserver.service;

import com.ontrustserver.domain.Post;
import com.ontrustserver.repository.PostRepository;
import com.ontrustserver.request.PostCreate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    public Post post(PostCreate postCreate) {
        Post post = Post.builder()
                .title(postCreate.title())
                .contents(postCreate.contents())
                .build();
        return postRepository.save(post);
    }

    public Post get(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
    }
}
