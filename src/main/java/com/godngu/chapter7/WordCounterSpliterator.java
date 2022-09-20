package com.godngu.chapter7;

import java.util.Spliterator;
import java.util.function.Consumer;

public class WordCounterSpliterator implements Spliterator<Character> {
    private final String string;
    private int currentChar = 0;

    public WordCounterSpliterator(String string) {
        this.string = string;
        System.out.println("length = " + string.length());
    }

    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        System.out.println("[tryAdvance]");
        char c = string.charAt(currentChar++);
        System.out.println("c = " + c);
        action.accept(c);
        return currentChar < string.length();
    }

    @Override
    public Spliterator<Character> trySplit() {
        System.out.println("\n[trySplit]");
        int currentSize = string.length() - currentChar;
        System.out.println("string.length: " + string.length() + ", currentChar: " + currentChar + ", currentSize: " + currentSize);
        if (currentSize < 10) {
            return null;
        }
        for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++) {
            System.out.println("splitPos = " + splitPos);
            char c = string.charAt(splitPos);
            System.out.println("split char = " + c);
            if (Character.isWhitespace(c)) {
                String substring = string.substring(currentChar, splitPos);
                System.out.println("substring = " + substring);
                Spliterator<Character> spliterator = new WordCounterSpliterator(substring);
                currentChar = splitPos;
                return spliterator;
            }
        }
        return null;
    }

    @Override
    public long estimateSize() {
        return string.length() - currentChar;
    }

    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}
