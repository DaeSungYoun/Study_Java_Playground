package com.ydskingdom.completableFuture.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class CompletableFutureTest01 {

    @DisplayName("runAsync()_thenRun()")
    @Test
    void test_case_01() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            // ForkJoinPool.commonPool-worker-3
        }).thenRun(() -> {
            System.out.println(Thread.currentThread().getName());
            // ForkJoinPool.commonPool-worker-3
        });

        future.join();
    }

    @DisplayName("supplyAsync()_multi")
    @Test
    void test_case_0111() {
//        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        System.out.println(Runtime.getRuntime().availableProcessors());

        List<Integer> integerList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            integerList.add(i);
        }

        List<CompletableFuture<Integer>> collect = integerList.stream().map(i -> CompletableFuture.supplyAsync(() -> {
                    System.out.println(Thread.currentThread().getName() + " : " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return i;
                }, executorService))
                .collect(Collectors.toList());

        List<Integer> collect1 = collect.stream().map(CompletableFuture::join).collect(Collectors.toList());
        System.out.println(collect1);
    }

    @DisplayName("supplyAsync()_thenApply()")
    @Test
    void test_case_02() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return "CompletableFuture";
        }).thenApply(s -> {
            System.out.println(Thread.currentThread().getName());
            return s.toUpperCase();
        });
        System.out.println(completableFuture.join());
    }

    @DisplayName("supplyAsync()_thenAcceptAsync()")
    @Test
    void test_case_03() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return "CompletableFuture";
        }, executorService).thenAcceptAsync(s -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(s + " Callback");
        }, executorService);

        executorService.shutdown();
    }

    @DisplayName("supplyAsync()_thenCompose()")
    @Test
    void test_case_04() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return "Hello";
        });

        //future 작업 이후 반환된 값을 사용하여 다음 작업 실행
        CompletableFuture<String> future1 = future.thenCompose(message -> getWorld(message));

        System.out.println(future1.join());

        CompletableFuture<String> future2 = future.thenCompose(message -> CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return message + " Hi";
        }));

        System.out.println(future2.join());
    }

    private CompletableFuture<String> getWorld(String message) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return message + " World!";
        });
    }

    /*
    thenCombine()은 두 작업을 독립적으로 실행하고 둘 다 종료 되었을 때 콜백을 실행

     */
    @DisplayName("supplyAsync()_thenCombine()_case01")
    @Test
    void test_case_05() {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return "Hello";
        });

        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return "World";
        });

        CompletableFuture<String> result = hello.thenCombine(world, (helloFutureReturnValue, worldFutureReturnValue)
                -> helloFutureReturnValue + " " + worldFutureReturnValue);

        System.out.println(result.join());
    }

    @DisplayName("supplyAsync()_thenCombine()_case02")
    @Test
    void test_case_06() {
        log.info("start");
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            log.info("helloFuture start");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(Thread.currentThread().getName());
            return "Hello";
        });

        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
            log.info("worldFuture start");
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(Thread.currentThread().getName());
            return "World";
        });

        CompletableFuture<String> result = hello.thenCombine(world, (helloFutureReturnValue, worldFutureReturnValue)
                -> helloFutureReturnValue + " " + worldFutureReturnValue);

        log.info(result.join());
    }

    @DisplayName("supplyAsync()_allOf()")
    @Test
    void test_case_07() {
        log.info("1");
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("helloFuture start, {}", threadName);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("helloFuture end, {}", threadName);
            return "Hello";
        });

        log.info("2");
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("worldFuture start, {}", threadName);
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("worldFuture end, {}", threadName);
            return "World";
        });

        log.info("3");
        CompletableFuture<String> hi = CompletableFuture.supplyAsync(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("hiFuture start, {}", threadName);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("hiFuture end, {}", threadName);
            return "hi";
        });

        log.info("4");
        List<CompletableFuture<String>> futures = Arrays.asList(hello, world, hi);

        log.info("5");
        CompletableFuture[] futuresArray = futures.toArray(new CompletableFuture[futures.size()]);

        log.info("6");
        CompletableFuture<List<String>> results = CompletableFuture.allOf(futuresArray)
                .thenApply(v -> {
                    log.info("allOf() callback start");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    List<String> collect = futures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList());

                    log.info("allOf() callback end");
                    log.info("collect : {}", collect);
                    return collect;
                });

        log.info("results : {}", results.join());

        /*
        17:06:58.131 [Test worker] INFO com.ydskingdom.completableFuture.service.CompletableFutureTest01 - 1
        17:06:58.137 [Test worker] INFO com.ydskingdom.completableFuture.service.CompletableFutureTest01 - 2
        17:06:58.138 [Test worker] INFO com.ydskingdom.completableFuture.service.CompletableFutureTest01 - 3
        17:06:58.139 [Test worker] INFO com.ydskingdom.completableFuture.service.CompletableFutureTest01 - 4
        17:06:58.138 [ForkJoinPool.commonPool-worker-3] INFO com.ydskingdom.completableFuture.service.CompletableFutureTest01 - helloFuture start,  ForkJoinPool.commonPool-worker-3
        17:06:58.140 [Test worker] INFO com.ydskingdom.completableFuture.service.CompletableFutureTest01 - 5
        17:06:58.141 [Test worker] INFO com.ydskingdom.completableFuture.service.CompletableFutureTest01 - 6
        17:06:58.143 [ForkJoinPool.commonPool-worker-5] INFO com.ydskingdom.completableFuture.service.CompletableFutureTest01 - worldFuture start,  ForkJoinPool.commonPool-worker-5
        17:06:58.143 [ForkJoinPool.commonPool-worker-7] INFO com.ydskingdom.completableFuture.service.CompletableFutureTest01 - hiFuture start,     ForkJoinPool.commonPool-worker-7
        17:07:00.150 [ForkJoinPool.commonPool-worker-3] INFO com.ydskingdom.completableFuture.service.CompletableFutureTest01 - helloFuture end,    ForkJoinPool.commonPool-worker-3
        17:07:03.144 [ForkJoinPool.commonPool-worker-7] INFO com.ydskingdom.completableFuture.service.CompletableFutureTest01 - hiFuture end,       ForkJoinPool.commonPool-worker-7
        17:07:05.145 [ForkJoinPool.commonPool-worker-5] INFO com.ydskingdom.completableFuture.service.CompletableFutureTest01 - worldFuture end,    ForkJoinPool.commonPool-worker-5
        17:07:05.146 [ForkJoinPool.commonPool-worker-5] INFO com.ydskingdom.completableFuture.service.CompletableFutureTest01 - allOf() callback start
        17:07:15.148 [ForkJoinPool.commonPool-worker-5] INFO com.ydskingdom.completableFuture.service.CompletableFutureTest01 - allOf() callback end
        17:07:15.149 [ForkJoinPool.commonPool-worker-5] INFO com.ydskingdom.completableFuture.service.CompletableFutureTest01 - collect : [Hello, World, hi]
        17:07:15.150 [Test worker] INFO com.ydskingdom.completableFuture.service.CompletableFutureTest01 - results : [Hello, World, hi]

        helloFuture, worldFuture, hiFuture는 각자 비동기로 실행
        allOf()는 모든 작업 결과에 콜백을 실행함
        allOf()는 지정한 비동기 작업들을 그룹핑?해서 작업들이 다 끝나고나서 콜백을 실행함
         */

        log.info("=========================Stream을 써서 값 가져오기===============================");
        List<String> collect = Stream.of(hello, world, hi)
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        log.info("collect : {}", collect);
    }

    /*
    비동기 작업들 중에 제일 먼저 완료된 작업에 대해 콜백을 실행
     */
    @DisplayName("supplyAsync()_anyOf()")
    @Test
    void test_case_08() {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("Hello start, {}", threadName);

            try {
                Thread.sleep((long) (Math.random() * 10000) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.info("Hello end, {}", threadName);
            return "Hello";
        });

        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("World start, {}", threadName);

            try {
                Thread.sleep((long) (Math.random() * 10000) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.info("World end, {}", threadName);
            return "World";
        });

        CompletableFuture<String> hi = CompletableFuture.supplyAsync(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("Hi start, {}", threadName);

            try {
                Thread.sleep((long) (Math.random() * 10000) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.info("Hi end, {}", threadName);
            return "Hi";
        });

        CompletableFuture<Void> future = CompletableFuture.anyOf(hello, world, hi)
                .thenAccept(v -> {
                    log.info("v : {}", v);
                });

        future.join();
    }

    @DisplayName("supplyAsync()_exceptionally()")
    @Test
    void test_case_09() {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("Hello start, {}", threadName);

            if (true) {
                throw new IllegalArgumentException("error!!!");
            }

            log.info("Hello end, {}", threadName);
            return "Hello";
        }).exceptionally(ex -> {
            log.error("ex : {}", ex);
            return "Error!";
        });

        log.info("hello : {}", hello.join());
    }

    @DisplayName("supplyAsync()_handle()")
    @Test
    void test_case_10() {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("Hello start, {}", threadName);

            if (true) {
                throw new IllegalArgumentException("error!!!");
            }

            log.info("Hello end, {}", threadName);
            return "Hello";
        }).handle((result, ex) -> {
            if (ex != null) {
                log.error("ex : {}", ex);
                return "Error!";
            }

            return result;
        });

        log.info("hello : {}", hello.join());
    }
}
