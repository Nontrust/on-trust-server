package com.ontrustserver.repository;


import com.ontrustserver.domain.post.Post;
import com.ontrustserver.request.PagingRequest;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getPostList(int page, int size, String order);
    List<Post> getPostList(PagingRequest pagingRequest);
    Post fetchAnyOne();
}
