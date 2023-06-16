package com.ontrustserver.domain.badword.dao;

import com.ontrustserver.domain.model.BadWord;
import com.ontrustserver.domain.model.enumerate.Language;

import java.util.List;

public interface BadWordRepositoryCustom {
    List<BadWord> getBadWordList(Language language);

}
