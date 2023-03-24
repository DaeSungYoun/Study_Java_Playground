package com.ydskingdom.optional;

import org.junit.jupiter.api.Test;

import java.util.Optional;

public class OptionalTest01 {

    @Test
    void OptionalTest01_01() {
        Optional<String> optional1 = Optional.empty();
        System.out.println(optional1.isPresent());
        System.out.println(optional1.isEmpty());

        Optional<String> optional2 = Optional.of("coco");
        System.out.println(optional2.isPresent());
        System.out.println(optional2.isEmpty());

        Optional<String> optional3 = Optional.ofNullable(null);
        System.out.println(optional3.isPresent());
        System.out.println(optional3.isEmpty());

        Optional<String> optional4 = Optional.ofNullable("hi");
        System.out.println(optional4.isPresent());
        System.out.println(optional4.isEmpty());
    }
}
