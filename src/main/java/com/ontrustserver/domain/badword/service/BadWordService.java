package com.ontrustserver.domain.badword.service;

import com.ontrustserver.domain.badword.dao.BadWordRepository;
import com.ontrustserver.domain.badword.dto.BadWordRequest;
import com.ontrustserver.domain.model.BadWord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BadWordService {
    private final BadWordRepository badWordRepository;

    // Todo: Read가 많은 엔티티로 전파 레벨 조정 필요
    @Transactional(timeout = 3, rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void setBadWord(BadWordRequest badWordRequest) {
        BadWord badWord = new BadWord(badWordRequest);
        badWordRepository.save(badWord);
    }
}
