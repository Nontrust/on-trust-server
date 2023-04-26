package com.ontrustserver.service;

import com.ontrustserver.domain.post.Post;
import com.ontrustserver.repository.PostRepository;
import com.ontrustserver.request.PostRequest;
import com.ontrustserver.response.PostResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository  postRepository;
    public PostResponse post(PostRequest postRequest) {
        Post post = new Post(postRequest);
        postRepository.save(post);

        return PostResponse.postToResponse(post);
    }

    public List<PostResponse> postList(List<PostRequest> postRequests) {
        List<Post> postList = postRequests.stream()
                .map(Post::new)
                .collect(Collectors.toList());

        return postRepository.saveAll(postList).stream()
                .map(PostResponse::postToResponse)
                .collect(Collectors.toList());
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return PostResponse.postToResponse(post);
    }

    public List<PostResponse> getPostList(int page, int size, String order) {

        return postRepository.getPostList(page, size, order).stream()
                .map(PostResponse::postToResponse)
                .collect(Collectors.toList());
    }
}
