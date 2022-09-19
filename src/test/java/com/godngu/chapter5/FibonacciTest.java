package com.godngu.chapter5;

import com.godngu.Fibonacci;
import org.junit.jupiter.api.Test;

class FibonacciTest {

    @Test
    void test1() {
        new Fibonacci().printPair(20);
    }

    @Test
    void test2() {
        new Fibonacci().printSequence(20);
    }
}
