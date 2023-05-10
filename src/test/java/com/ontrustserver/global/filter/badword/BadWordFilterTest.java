package com.ontrustserver.global.filter.badword;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class BadWordFilterTest {

    @Test
    @DisplayName("영어 필터링 메서드 테스트")
    void EngBadWordTest(){
        // given
        String badSentence = TestSentence.LOREM_IPSUM_CONTAIN_DAMN;
        String goodSentence = TestSentence.LOREM_IPSUM;
        BadWordInterface eng = new EngBadWord();

        // when
        Optional<String> containAbuse = eng.containAbuseSentence(badSentence);
        Optional<String> nonContainAbuse = eng.containAbuseSentence(goodSentence);

        Optional<String> containAbuseParallel = eng.containAbuseSentenceParallel(badSentence);
        Optional<String> nonContainAbuseParallel = eng.containAbuseSentenceParallel(goodSentence);

        //then

        assertTrue(containAbuse.isPresent());
        assertFalse(nonContainAbuse.isPresent());

        assertTrue(containAbuseParallel.isPresent());
        assertFalse(nonContainAbuseParallel.isPresent());
    }

    @Test
    @DisplayName("국문 필터링 메서드 테스트")
    void KorBadWordTest(){
        // given
        String badSentence = TestSentence.HUN_MIN_JEONG_EUM_CONTAIN_BAD_SENTENCE;
        String goodSentence = TestSentence.HUN_MIN_JEONG_EUM;
        BadWordInterface kor = new KorBadWord();

        // when
        Optional<String> containAbuse = kor.containAbuseSentence(badSentence);
        Optional<String> nonContainAbuse = kor.containAbuseSentence(goodSentence);

        Optional<String> containAbuseParallel = kor.containAbuseSentenceParallel(badSentence);
        Optional<String> nonContainAbuseParallel = kor.containAbuseSentenceParallel(goodSentence);

        //then
        assertTrue(containAbuse.isPresent());
        assertEquals(containAbuse.get(), KorBadWord.BadWord.JERK.getSentence());
        assertFalse(nonContainAbuse.isPresent());

        assertTrue(containAbuseParallel.isPresent());
        assertEquals(containAbuseParallel.get(), KorBadWord.BadWord.JERK.getSentence());
        assertFalse(nonContainAbuseParallel.isPresent());
    }
    @Test
    @DisplayName("병렬 처리 시간 검사 : 반드시 통과하지 않을 수도 있음")
    void checkKorParallelTime(){
        // given
        KorBadWord kor = new KorBadWord();
        StopWatch stopWatch = new StopWatch();

        String blob = TestSentence.HUN_MIN_JEONG_EUM.repeat(10000);

        String blobWithBadWord = TestSentence.HUN_MIN_JEONG_EUM.repeat(1000)
                +KorBadWord.BadWord.FUCK.getSentence()
                +KorBadWord.BadWord.RETARD.getSentence()
                +TestSentence.HUN_MIN_JEONG_EUM.repeat(9000);

        String blobWithBadWordLast = TestSentence.HUN_MIN_JEONG_EUM.repeat(10000)
                +KorBadWord.BadWord.FUCK.getSentence();

        // when
        // 일반적인 큰 단어 검사 시
        // Non Parallel
        stopWatch.start();
        kor.containAbuseSentence(blob);
        stopWatch.stop();
        long blobTime = stopWatch.getLastTaskTimeNanos();
        // parallel
        stopWatch.start();
        kor.containAbuseSentenceParallel(blob);
        stopWatch.stop();
        long blobTimeParallel = stopWatch.getLastTaskTimeNanos();

        // 욕설이 중간에 포함된 단어 검사 시
        // Non Parallel
        stopWatch.start();
        kor.containAbuseSentence(blobWithBadWord);
        stopWatch.stop();
        long blobBadWordTime = stopWatch.getLastTaskTimeNanos();

        // Parallel
        stopWatch.start();
        kor.containAbuseSentenceParallel(blobWithBadWord);
        stopWatch.stop();
        long blobBadWordTimeParallel = stopWatch.getLastTaskTimeNanos();

        // 욕설이 마지막에 포함된 단어 검사 시
        // Non Parallel
        stopWatch.start();
        kor.containAbuseSentenceParallel(blobWithBadWordLast);
        stopWatch.stop();
        long blobTimeWorst = stopWatch.getLastTaskTimeNanos();

        // Parallel
        stopWatch.start();
        kor.containAbuseSentenceParallel(blobWithBadWordLast);
        stopWatch.stop();
        long blobTimeWorstParallel = stopWatch.getLastTaskTimeNanos();

        log.warn("blobTime 길이 {} / 시간 (일반-병렬={})", blob.length(), blobTime - blobTimeParallel);
        log.warn("blobBadWordTime 길이 {} / 검사 시간 (일반-병렬={})", blobWithBadWord.length(), blobBadWordTime - blobBadWordTimeParallel);
        log.warn("blobTimeWorst 길이 {} / 검사 시간 (일반-병렬={})", blobWithBadWordLast.length(), blobTimeWorst - blobTimeWorstParallel);

        // then
        /*
        assertTrue(blobTime >= blobTimeParallel);
        assertTrue(blobBadWordTime >= blobBadWordTimeParallel);
        assertTrue(blobTimeWorst >= blobTimeWorstParallel);
         */
    }
}
