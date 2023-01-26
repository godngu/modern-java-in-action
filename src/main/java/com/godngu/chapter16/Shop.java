package com.godngu.chapter16;

import com.godngu.chapter16.Discount.Code;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop {

    private final String name;

    public Shop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private final Random random = new Random();

    public String getPrice(String product) {
        double price = calculatePrice(product);
        Code code = Code.values()[random.nextInt(Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }

    public Future<Double> getPriceAsync(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    private double calculatePrice(String product) {
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
