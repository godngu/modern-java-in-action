package com.godngu.chapter16;

public class Main {


    public static void main(String[] args) {
        System.out.println("Thread counts: " + Runtime.getRuntime().availableProcessors());
        Playground playground = new Playground();
        playground.executeFindPrices();
    }


}
