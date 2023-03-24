package com.ydskingdom.completableFuture.service;

import com.ydskingdom.completableFuture.domain.Coffee;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface CoffeeUseCase {
    int getPrice(String name);

    Future<Integer> getPriceAsync(String name);

    Future<Integer> getDiscountPriceAsync(Integer price);

    CompletableFuture<Coffee> makeCoffee_SupplyAsync(String name);

}
