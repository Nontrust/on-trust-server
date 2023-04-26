package com.ontrustserver.repository;


import com.ontrustserver.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getPostList(int page, int size, String order);
}
