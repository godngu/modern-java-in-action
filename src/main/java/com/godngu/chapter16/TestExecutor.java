package com.godngu.chapter16;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestExecutor {

    private final List<Shop> shops = Arrays.asList(
        new Shop("BestPrice"),
        new Shop("LestsSaveBig"),
        new Shop("MyFavoriteShop"),
        new Shop("BuyItAll"),
        new Shop("11Street"),
        new Shop("ebay"),
        new Shop("Amazon"),
        new Shop("GMarket"),
        new Shop("WeMakePrice"),
        new Shop("Interpark")
    );

    private final Executor executor = Executors.newFixedThreadPool(
        shops.size(), r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    );

    public void executeFindPrices() {
        long start = System.nanoTime();
        System.out.println(findPrices("myPhone27S"));
//        System.out.println(findPricesParallelStream("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    private List<String> findPrices(String product) {
        return shops.stream()
            .map(shop -> shop.getPrice(product))
            .map(Quote::parse)
            .map(Discount::applyDiscount)
            .collect(toList());
    }

    public String format(String name, double price) {
        return String.format("%s price is %.2f", name, price);
    }

    public void executeAsync() {
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
}
