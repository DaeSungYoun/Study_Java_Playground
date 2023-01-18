package com.ydskingdom.completableFuture.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Coffee {

    private String name;

    private int price;
}
