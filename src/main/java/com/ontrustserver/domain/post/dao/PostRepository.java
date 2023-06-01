package com.ontrustserver.domain.post.dao;


import com.ontrustserver.domain.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {}
