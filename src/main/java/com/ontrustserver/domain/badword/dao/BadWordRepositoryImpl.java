package com.ontrustserver.domain.badword.dao;

import com.ontrustserver.domain.model.QBadWord;
import com.ontrustserver.domain.model.enumerate.Language;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BadWordRepositoryImpl implements BadWordRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<String> getBadWordList(Language language) {
        return jpaQueryFactory.select(QBadWord.badWord.sentence)
                .from(QBadWord.badWord)
                .where(QBadWord.badWord.language.eq(language))
                .fetch();
    }
}
