package com.ontrustserver.repository;


import com.ontrustserver.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
