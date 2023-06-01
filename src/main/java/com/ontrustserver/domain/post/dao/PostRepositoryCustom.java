package com.ontrustserver.domain.post.dao;


import com.ontrustserver.domain.model.Post;
import com.ontrustserver.global.common.dto.response.PagingRequest;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getPostList(int page, int size, String order);
    List<Post> getPostList(PagingRequest pagingRequest);
    Post fetchAnyOne();
    List<Long> getPostIdList(int size);
}
