package com.ontrustserver.global.aspect.badword.domain;

import com.ontrustserver.domain.badword.dao.BadWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.TreeSet;

import static com.ontrustserver.domain.model.enumerate.Language.KOREAN;

@RequiredArgsConstructor
@Component
public class KorBadWord implements BadWordInterface {
    private final BadWordRepository badWordRepository;
    @Override
    public Optional<String> containAbuseSentence(String text) {
        return getBadWordMap().stream()
                .filter(text::contains)
                .findFirst();
    }

    @Override
    public Optional<String> containAbuseSentenceParallel(String blob) {
        //Todo: 병렬 스트리밍 문제 시 HashTable 사용 예정
        return getBadWordMap().parallelStream()
                .filter(blob::contains)
                .findFirst();
    }

    @Override
    public TreeSet<String> getBadWordMap() {
        return new TreeSet<>(badWordRepository.getBadWordList(KOREAN));
    }
}
