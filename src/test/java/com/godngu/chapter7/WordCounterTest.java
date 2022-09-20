package com.godngu.chapter7;

import static com.godngu.chapter7.WordCounter.countWords;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Test;

class WordCounterTest {

    final String SENTENCE = "Nel     mezzo del cammin di nostra vita "
        + "mi ritrovai in una   selva oscura "
        + "ch   la dritta via era  smarrita";

    private Stream<Character> toStream() {
        return IntStream.range(0, SENTENCE.length())
            .mapToObj(SENTENCE::charAt);
    }

    @Test
    void sequential() {
        System.out.println("Found " + countWords(toStream()) + " words");
    }

    @Test
    void parallel() {
        System.out.println("Found " + countWords(toStream().parallel()) + " words");
    }

    @Test
    void spliterator() {
        WordCounterSpliterator spliterator = new WordCounterSpliterator(SENTENCE);
        Stream<Character> stream = StreamSupport.stream(spliterator, true);// boolean: 병렬 스트림 생성 여부
        System.out.println("Found " + countWords(stream) + " words");
    }
}
