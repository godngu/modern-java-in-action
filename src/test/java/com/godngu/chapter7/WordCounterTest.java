package com.godngu.chapter7;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class WordCounterTest {

    final String SENTENCE = """
        Nel     mezzo del cammin di nostra vita 
        mi ritrovai in una   selva oscura
        ch   la dritta via era  smarrita 
        """;

    private Stream<Character> toStream() {
        return IntStream.range(0, SENTENCE.length())
            .mapToObj(SENTENCE::charAt);
    }

    @Test
    void sequential() {
        System.out.println("Found " + WordCounter.countWords(toStream()) + " words");
    }

    @Test
    void parallel() {
        System.out.println("Found " + WordCounter.countWords(toStream().parallel()) + " words");
    }
}
