package com.ontrustserver.domain.badword.dao;

import com.ontrustserver.domain.model.BadWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadWordRepository extends JpaRepository<BadWord, String>, BadWordRepositoryCustom{
}
