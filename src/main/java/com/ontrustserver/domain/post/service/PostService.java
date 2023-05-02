package com.ontrustserver.domain.post.service;

import com.ontrustserver.domain.model.Post;
import com.ontrustserver.domain.model.PostEditor;
import com.ontrustserver.domain.post.dao.PostRepository;
import com.ontrustserver.domain.post.util.ResponseUtil;
import com.ontrustserver.global.common.request.PagingRequest;
import com.ontrustserver.domain.post.dto.request.PostRequest;
import com.ontrustserver.domain.post.dto.response.PostEdit;
import com.ontrustserver.domain.post.dto.response.PostResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository  postRepository;
    private final ResponseUtil response;
    public PostResponse post(PostRequest postRequest) {
        Post post = new Post(postRequest);
        postRepository.save(post);

        return response.of(post);
    }

    public List<PostResponse> postList(List<PostRequest> postRequests) {
        List<Post> postList = postRequests.stream()
                .map(Post::new)
                .collect(Collectors.toList());

        return postRepository.saveAll(postList).stream()
                .map(ResponseUtil::of)
                .collect(Collectors.toList());
    }

    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return response.of(post);
    }

    public List<PostResponse> getPostList(PagingRequest pagingRequest) {
        return postRepository.getPostList(pagingRequest).stream()
                .map(ResponseUtil::of)
                .collect(Collectors.toList());
    }

    @Transactional(timeout = 3, rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public PostResponse updatePostById(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        PostEditor postEditor = editorBuilder.
                title(postEdit.title())
                .contents(postEdit.contents())
                .build();

        post.edit(postEditor);

        return PostResponse.postToResponse(post);
    }
}
