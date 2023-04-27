package com.ontrustserver.service;

import com.ontrustserver.domain.post.Post;
import com.ontrustserver.repository.PostRepository;
import com.ontrustserver.request.PagingRequest;
import com.ontrustserver.request.PostRequest;
import com.ontrustserver.response.PostEdit;
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

    public PostResponse getById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return PostResponse.postToResponse(post);
    }

    public List<PostResponse> getPostList(PagingRequest pagingRequest) {
        return postRepository.getPostList(pagingRequest).stream()
                .map(PostResponse::postToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(timeout = 3, rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public PostResponse updatePostById(Long id, PostEdit postEdit){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
        post.setTitle(postEdit.title());
        post.setContents(postEdit.contents());

        return PostResponse.postToResponse(post);
    }
}
