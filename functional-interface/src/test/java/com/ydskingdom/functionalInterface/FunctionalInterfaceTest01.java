package com.ydskingdom.functionalInterface;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

public class FunctionalInterfaceTest01 {

    @Test
    void test_case_01() {
        Supplier<String> stringSupplier = () -> "Hello, Supplier!";
        String stringValue = stringSupplier.get();
        System.out.println("stringValue : " + stringValue);

        Supplier<Double> randomSupplier = Math::random;
        Double aDouble = randomSupplier.get();
        System.out.println("aDouble : " + aDouble);
    }
}
