package com.ontrustserver.repository;

import com.ontrustserver.domain.post.Post;
import com.ontrustserver.domain.post.QPost;
import com.ontrustserver.request.PagingRequest;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getPostList(int page, int size, String order) {
        return jpaQueryFactory.selectFrom(QPost.post)
                .limit(size)
                .offset((page - 1L) * size)
                .orderBy(orderById(order))
                .fetch();
    }

    @Override
    public List<Post> getPostList(PagingRequest pagingRequest) {
        return jpaQueryFactory.selectFrom(QPost.post)
                .limit(pagingRequest.size())
                .offset(pagingRequest.offSet())
                .orderBy(orderById(pagingRequest.order()))
                .fetch();
    }

    private static OrderSpecifier<Long> orderById(String order){
        QPost post = QPost.post;
        return order.equalsIgnoreCase("desc")
                ? post.id.desc()
                : post.id.asc();
    }
}
