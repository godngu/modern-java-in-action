package com.godngu.chapter16;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

public class Playground {

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

    private List<String> findPricesParallelStream(String product) {
        return shops.parallelStream()
            .map(shop -> format(shop.getName(), shop.getPrice(product)))
            .collect(toList());
    }

    private List<String> findPrices(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
            .map(shop -> CompletableFuture.supplyAsync(
                () -> format(shop.getName(), shop.getPrice(product)), executor
            ))
            .collect(toList());

        return priceFutures.stream()
            .map(CompletableFuture::join)
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

    public void execute() {
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
