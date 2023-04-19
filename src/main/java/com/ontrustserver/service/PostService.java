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
    public void post(PostCreate postCreate) {
        Post post = Post.builder()
                .title(postCreate.title())
                .contents(postCreate.contents())
                .build();

        postRepository.save(post);
    }
}
