package com.godngu.chapter5;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TransactionTest {

    private List<Transaction> getTransactions() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        return Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
        );
    }

    @Test
    @DisplayName("1. 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오.")
    void test1() {
        // given
        List<Transaction> transactions = getTransactions();

        // when
        List<Transaction> result = transactions.stream()
            .filter(t -> t.getYear() == 2011)
            .sorted(comparing(Transaction::getValue))
            .collect(toList());

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getValue()).isEqualTo(300);
        assertThat(result.get(0).getTrader().getName()).isEqualTo("Brian");
        assertThat(result.get(1).getValue()).isEqualTo(400);
        assertThat(result.get(1).getTrader().getName()).isEqualTo("Raoul");
    }

    @Test
    @DisplayName("2_1. 거래자가 근무하는 모든 도시를 중복 없이 나열하시오.")
    void test2_1() {
        // given
        List<Transaction> transactions = getTransactions();

        // when
        List<String> cities = transactions.stream()
            .map(t -> t.getTrader().getCity())
            .distinct()
            .collect(toList());

        // then
        assertThat(cities).hasSize(2);
        assertThat(cities).anyMatch(t -> t.contains("Cambridge"));
        assertThat(cities).anyMatch(t -> t.contains("Milan"));
    }

    @Test
    @DisplayName("2_2. 거래자가 근무하는 모든 도시를 중복 없이 나열하시오.")
    void test2_2() {
        // given
        List<Transaction> transactions = getTransactions();

        // when
        Set<String> cities = transactions.stream()
            .map(t -> t.getTrader().getCity())
            .collect(toSet());

        // then
        assertThat(cities).hasSize(2);
        assertThat(cities).anyMatch(t -> t.contains("Cambridge"));
        assertThat(cities).anyMatch(t -> t.contains("Milan"));
    }

    @Test
    @DisplayName("3. 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.")
    void test3() {
        // given
        List<Transaction> transactions = getTransactions();

        // when
        List<Trader> names = transactions.stream()
            .map(Transaction::getTrader)
            .filter(t -> t.getCity().equalsIgnoreCase("Cambridge"))
            .distinct()
            .sorted(comparing(Trader::getName))
            .collect(toList());

        // then
        assertThat(names).hasSize(3);
        assertThat(names.get(0).getName()).isEqualTo("Alan");
        assertThat(names.get(1).getName()).isEqualTo("Brian");
        assertThat(names.get(2).getName()).isEqualTo("Raoul");
    }

    @Test
    @DisplayName("4. 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오.")
    void test4() {
        // given
        List<Transaction> transactions = getTransactions();

        // when
        List<Trader> names = transactions.stream()
            .map(Transaction::getTrader)
            .distinct()
            .sorted(comparing(Trader::getName))
            .collect(toList());

        // then
        assertThat(names).hasSize(4);
        assertThat(names.get(0).getName()).isEqualTo("Alan");
        assertThat(names.get(1).getName()).isEqualTo("Brian");
        assertThat(names.get(2).getName()).isEqualTo("Mario");
        assertThat(names.get(3).getName()).isEqualTo("Raoul");
    }

    @Test
    @DisplayName("5. 밀라노에 거래자가 있는가?")
    void test5() {
        // given
        List<Transaction> transactions = getTransactions();

        // when
        boolean milan = transactions.stream()
            .anyMatch(t -> t.getTrader().getCity().equalsIgnoreCase("milan"));

        // then
        assertThat(milan).isTrue();
    }

    @Test
    @DisplayName("6_1. 케임브리지에 거주하는 거래자의 모든 트랜잭션값을 출력하시오.")
    void test6_1() {
        // given
        List<Transaction> transactions = getTransactions();

        // when
        int sum = transactions.stream()
            .filter(t -> t.getTrader().getCity().equalsIgnoreCase("Cambridge"))
            .mapToInt(Transaction::getValue)
            .sum();

        // then
        assertThat(sum).isEqualTo(300 + 1000 + 400 + 950);
    }

    @Test
    @DisplayName("6_2. 케임브리지에 거주하는 거래자의 모든 트랜잭션값을 출력하시오.")
    void test6_2() {
        // given
        List<Transaction> transactions = getTransactions();

        // when
        int sum = transactions.stream()
            .filter(t -> t.getTrader().getCity().equalsIgnoreCase("Cambridge"))
            .map(Transaction::getValue)
            .reduce(0, Integer::sum);

        // then
        assertThat(sum).isEqualTo(300 + 1000 + 400 + 950);
    }

    @Test
    @DisplayName("7. 전체 트랜잭션 중 최댓값은 얼마인가?")
    void test7() {
        // given
        List<Transaction> transactions = getTransactions();

        // when
        int max = transactions.stream()
            .mapToInt(Transaction::getValue)
            .max()
            .orElse(0);

        // then
        assertThat(max).isEqualTo(1000);
    }

    @Test
    @DisplayName("8. 전체 트랜잭션 중 최솟값은 얼마인가?")
    void test8() {
        // given
        List<Transaction> transactions = getTransactions();

        // when
        Integer min = transactions.stream()
            .map(Transaction::getValue)
            .reduce(Integer.MAX_VALUE, Integer::min);

        // then
        assertThat(min).isEqualTo(300);
    }
}
