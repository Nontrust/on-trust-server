package com.ontrustserver.domain.post.dao;

import com.ontrustserver.domain.model.Post;
import com.ontrustserver.domain.model.QPost;
import com.ontrustserver.global.common.dto.response.PagingRequest;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

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

    public Post fetchAnyOne(){
        return jpaQueryFactory.selectFrom(QPost.post)
                .limit(1)
                .fetchFirst();
    }

    @Override
    public List<Long> getPostIdList(int size) {
        return jpaQueryFactory.select(QPost.post.id)
                .from(QPost.post)
                .limit(size)
                .fetch();
    }

    private static OrderSpecifier<Long> orderById(String order){
        QPost post = QPost.post;
        return order.equalsIgnoreCase("desc")
                ? post.id.desc()
                : post.id.asc();
    }
}
