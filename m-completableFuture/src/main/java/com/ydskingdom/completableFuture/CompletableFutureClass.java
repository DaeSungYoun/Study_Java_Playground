package com.ydskingdom.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureClass {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName()); // [결과]: ForkJoinPool.commonPool-worker-19
            return "Hello";
        });

        CompletableFuture<String> future = hello.thenCompose(message -> getWorld(message));
        System.out.println(future.get()); // [결과]: Hello World!

    }

    private static CompletableFuture<String> getWorld(String message) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName()); // [결과]: ForkJoinPool.commonPool-worker-5
            return message + " World!";
        });
    }
}