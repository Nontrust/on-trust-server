package com.ontrustserver.domain.badword.dao;

import com.ontrustserver.domain.model.enumerate.Language;

import java.util.List;

public interface BadWordRepositoryCustom {
    List<String> getBadWordList(Language language);

}
