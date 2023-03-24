package com.ydskingdom.webflux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class webfluxTest01 {

    @Test
    void test_case_01() {
        Flux.just(1, 2, 3)
                .publishOn(Schedulers.newSingle("PUB"))
                .map(item -> item * 10)
                .log()
                .subscribeOn(Schedulers.newSingle("SUB"))
                .subscribe(item -> System.out.println(Thread.currentThread().getName() + " : " + item));
    }

    @Test
    void test_case_02() {
        List<Integer> elements = new ArrayList<>();

        Flux.just(1, 2, 3, 4)
                .log()
                .subscribe(elements::add);

        assertThat(elements).containsExactly(1, 2, 3, 4);
    }

    @Test
    void test_case_03() {
        Flux<Integer> seq = Flux.just(1, 2, 3); // Integer 값을 발생하는 Flux 생성

        seq.subscribe(item -> System.out.println("데이터 : " + item));
    }

    @Test
    void test_case_04() {
        Flux.just(1, 2, 3)
                .doOnNext(i -> System.out.println("doOnNext: " + i))
                .log()
                .subscribe(i -> System.out.println("Received: " + i));


        System.out.println("====================================================");

        Flux<Integer> seq = Flux.just(1, 2, 3)
                .doOnNext(i -> System.out.println("doOnNext: " + i));
        System.out.println("시퀀스 생성");
        seq.subscribe(i -> System.out.println("Received: " + i));

        System.out.println("====================================================");
        Flux<Integer> seq1 = Flux.just(1, 2, 3);
        seq1.subscribe(v -> System.out.println("구독1: " + v)); // 구독
        seq1.subscribe(v -> System.out.println("구독2: " + v)); // 구독

        Consumer<Integer> customConsumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println(integer);
            }
        };

        Consumer<Throwable> throwableConsumer = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {

            }
        };

        Flux.just(1, 2, 3)
                .subscribe(customConsumer, throwableConsumer);
    }


}
