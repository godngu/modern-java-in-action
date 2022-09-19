package com.godngu.chapter7;

import static com.godngu.chapter7.PerformanceMeasurement.measureSumPerf;

import org.junit.jupiter.api.Test;

class ParallelStreamsTest {

    @Test
    void name() {
        System.out.println("Sequential sum done in: " +
            measureSumPerf(ParallelStreams::sequentialSum, 10_000_000) + " msecs \n");

        System.out.println("Iterative sum done in: " +
            measureSumPerf(ParallelStreams::iterativeSum, 10_000_000) + " msecs \n");

        System.out.println("Parallel sum done in: " +
            measureSumPerf(ParallelStreams::parallelSum, 10_000_000) + " msecs \n");
    }

}
