package com.ydskingdom.completableFuture.service;

import com.ydskingdom.completableFuture.domain.Coffee;
import com.ydskingdom.completableFuture.repository.CoffeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoffeeComponent implements CoffeeUseCase {
    private final CoffeeRepository coffeeRepository;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

//    Executor executor = Executors.newFixedThreadPool(10);

    public CompletableFuture<Void> runAsync(){
        log.info("RunAsync Start !");
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.info("RunAsync End!");
        });

        return future;
    }

    @Override
    public int getPrice(String name) {
        log.info("(바리스타) 커피 주문을 받는다");

        log.info("(손님) (Blocking) 커피 주문 후 나올때까지 그 자리에서 아무것도 하지 못하고 커피를 기다린다");
        log.info("(손님) (Blocking) 아무것도 하지 못하고 계속 커피를 기다린다");

        return coffeeRepository.getPriceByName(name);
    }

    @Override
    public CompletableFuture<Integer> getPriceAsync(String name) {
        log.info("(바리스타) 커피 주문을 받는다");

        return CompletableFuture.supplyAsync(() -> {
            log.info("(바리스타) 영수증을 준다");
            return coffeeRepository.getPriceByName(name);
            }, threadPoolTaskExecutor
        );
    }

    @Override
    public CompletableFuture<Integer> getDiscountPriceAsync(Integer price) {
        return CompletableFuture.supplyAsync(() -> {
                    log.info("supplyAsync");
                    return (int) (price * 0.9);
                }, threadPoolTaskExecutor
        );
    }

    @Override
    public CompletableFuture<Coffee> makeCoffee_SupplyAsync(String name) {
        return CompletableFuture.supplyAsync(() -> coffeeRepository.makeCoffee(name));
    }
}
