package com.godngu.chapter16;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
//        execute();
//        executeAsync();
        executeFindPrices();
    }

    private static void executeFindPrices() {
        long start = System.nanoTime();
        System.out.println(findPrices("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    private static List<String> findPrices(String product) {
        return createShops().parallelStream()
            .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
            .collect(toList());
    }

    private static List<Shop> createShops() {
        return Arrays.asList(
            new Shop("BestPrice"),
            new Shop("LestsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll")
        );
    }

    private static void executeAsync() {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
        long invocationTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Invocation returned after " + invocationTime + " msecs");

        // doSomethingElse();

        try {
            double price = futurePrice.get();
            System.out.printf("Price is %.2f%n", price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        long retrievalTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Price returned after " + retrievalTime + " msecs");
    }

    private static void execute() {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        double price = shop.getPrice("my favorite product");
        long invocationTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Invocation returned after " + invocationTime + " msecs");

        // doSomethingElse();

        System.out.printf("Price is %.2f%n", price);

        long retrievalTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Price returned after " + retrievalTime + " msecs");
    }
}
