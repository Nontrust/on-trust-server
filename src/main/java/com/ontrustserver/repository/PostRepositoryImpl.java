package com.ontrustserver.repository;

import com.ontrustserver.domain.post.Post;
import com.ontrustserver.domain.post.QPost;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getPostList(int page, int size, String order) {
        QPost post = QPost.post;
        OrderSpecifier<Long> orderById = order.equalsIgnoreCase("desc")
                ? post.id.desc()
                : post.id.asc();
        log.debug("orderById {}", orderById);

        return jpaQueryFactory.selectFrom(post)
                .limit(size)
                .offset((page - 1L) * size)
                .orderBy(orderById)
                .fetch();
    }
}
