package com.godngu.chapter7;

import java.util.stream.Stream;

public class ParallelStreams {

    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
            .limit(n)
            .reduce(0L, Long::sum);
    }

    public static long iterativeSum(long n) {
        long result = 0L;
        for (int i = 0; i <= n; i++) {
            result += i;
        }
        return result;
    }

    public static long parallelSum(long n) {
        return Stream.iterate(0L, i -> i + 1)
            .limit(n)
            .parallel()
            .reduce(0L, Long::sum);
    }
}
