package com.ydskingdom.completableFuture.service;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Slf4j
public class CompletableFutureTest02 {

    /*
    3개 작업을 비동기로 처리해야 할 때
    각 작업에 대해 예외처리를 적용한 케이스
     */
    @Test
    void exception_test() {
        CompletableFuture<User> a = CompletableFuture.supplyAsync(() -> task01());
        CompletableFuture<User> b = CompletableFuture.supplyAsync(() -> task02());
        CompletableFuture<User> c = CompletableFuture.supplyAsync(() -> task03());

        List<CompletableFuture<User>> futures = Arrays.asList(a, b, c);

        CompletableFuture[] futuresArray = futures.toArray(new CompletableFuture[futures.size()]);

        CompletableFuture<List<User>> listCompletableFuture = CompletableFuture.allOf(futuresArray).thenApply(v -> {
            List<User> collect = futures.stream()
                    .map(d -> d.exceptionally(ex -> null).join())
                    .filter(d -> !Objects.isNull(d))
                    .collect(Collectors.toList());
            return collect;
        });

        List<User> result = listCompletableFuture.join();
        log.info("results : {}", result);
    }

    public User task01() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new User("김씨", 1);
    }

    public User task02() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new User("이씨", 2);
    }

    public User task03() {
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            if (true) {
                throw new TimeoutException("Error");
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
            return null;
        }

        return new User("윤씨", 3);
    }

    @ToString
    public static class User{
        private String name;
        private int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
