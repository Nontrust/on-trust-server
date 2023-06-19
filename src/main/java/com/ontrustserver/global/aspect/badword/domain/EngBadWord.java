package com.ontrustserver.global.aspect.badword.domain;

import com.ontrustserver.domain.badword.dao.BadWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;
import java.util.TreeSet;

import static com.ontrustserver.domain.model.enumerate.Language.ENGLISH;

@RequiredArgsConstructor
@Component
public class EngBadWord implements BadWordInterface {
    private final BadWordRepository badWordRepository;

    @Override
    public Optional<String> containAbuseSentence(String text) {
        String textUpperCase = text.toLowerCase(Locale.ROOT);
        return getBadWordMap().stream()
                .filter(textUpperCase::contains)
                .findFirst();
    }

    @Override
    public Optional<String> containAbuseSentenceParallel(String blob) {
        String lowerCase = blob.toLowerCase(Locale.ROOT);
        //Todo: 병렬 스트리밍 문제 시 HashTable 사용 예정
        return getBadWordMap()
                .parallelStream()
                .filter(lowerCase::contains)
                .findFirst();
    }

    @Override
    public TreeSet<String> getBadWordMap() {
        return new TreeSet<>(badWordRepository.getBadWordList(ENGLISH));
    }
}
