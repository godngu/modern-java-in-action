package com.godngu.chapter7;

import static com.godngu.chapter7.PerformanceMeasurement.measureSumPerf;

import java.util.function.Function;
import org.junit.jupiter.api.Test;

class ForkJoinSumCalculatorTest {

    @Test
    void name() {
        System.out.println(
            "ForkJoin sum done in: " + measureSumPerf(ForkJoinSumCalculator::forkJoinSum, 10_000_000) + " msecs \n");
    }
}
