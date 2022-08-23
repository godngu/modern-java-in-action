package com.godngu.chapter5;

import java.util.stream.Stream;

public class Fibonacci {

    public void printPair(int limit) {
        Stream.iterate(new int[] {0, 1}, t -> new int[] {t[1], t[0] + t[1]})
            .limit(limit)
            .forEach(t -> System.out.println("(" + t[0] + ", " + t[1] + ")"));
    }

    public void printSequence(int limit) {
        Stream.iterate(new int[] {0, 1}, t -> new int[] {t[1], t[0] + t[1]})
            .limit(limit)
            .map(t -> t[0])
            .forEach(System.out::println);
    }
}
