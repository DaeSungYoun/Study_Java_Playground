package com.ydskingdom.completableFuture.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class CoffeeComponentTest {

    @Autowired
    CoffeeComponent coffeeComponent;

    @DisplayName("동기/블록킹")
    @Test
    void 가격_조회_동기_블록킹_호출_테스트() {
        /*
        (손님) 커피숍에 가서 커피를 주문한다
        (바리스타) 커피 주문을 받는다
        (손님) (Blocking) 커피 주문 후 나올때까지 그 자리에서 아무것도 하지 못하고 커피를 기다린다
        (손님) (Blocking) 아무것도 하지 못하고 계속 커피를 기다린다
        (바리스타) 커피 만들기 시작
        (바리스타) 커피 만들기 완료
        (손님) 커피를 받는다
        (손님) 커피를 가지고 자리로 가서 다른일을 한다.
         */
        int expectedPrice = 1100;

        log.info("(손님) 커피숍에 가서 커피를 주문한다");
        int resultPrice = coffeeComponent.getPrice("latte");
        log.info("(손님) 커피를 받는다");
        log.info("(손님) 커피를 가지고 자리로 가서 다른일을 한다.");

        assertThat(resultPrice).isEqualTo(expectedPrice);
    }

    @DisplayName("비동기/논블록킹/블록킹 case 1")
    @Test
    void 가격_조회_비동기_논블록킹_블록킹_호출_테스트1() {
        /*
        (손님) 커피숍에 가서 커피를 주문한다
        (바리스타) 커피 주문을 받는다
        (바리스타) 영수증을 준다
        (바리스타) 커피 만들기 시작
        (손님) (Non-Blocking) 영수증을 받은다음 자리에가서 논다
        (손님) (Non-Blocking) 자리에서 딴짓하며 계속 논다
        (손님) (Blocking) 영수증을 들고 커피 받는 곳에 가서 커피가 나왔는지 확인 후 커피를 기다린다
        (바리스타) 커피 만들기 완료
        (손님) 커피를 받는다

        join(), get()을 사용하게 되면 Blocking 포인트가 발생
         */
        int expectedPrice = 1100;

        log.info("(손님) 커피숍에 가서 커피를 주문한다");
        CompletableFuture<Integer> future = coffeeComponent.getPriceAsync("latte");
        log.info("(손님) (Non-Blocking) 영수증을 받은다음 자리에가서 논다");
        log.info("(손님) (Non-Blocking) 자리에서 딴짓하며 계속 논다");

        log.info("(손님) (Blocking) 영수증을 들고 커피 받는 곳에 가서 커피가 나왔는지 확인 후 커피를 기다린다");
        int resultPrice = future.join();
        log.info("(손님) 커피를 받는다");

        assertThat(resultPrice).isEqualTo(expectedPrice);
    }

    @DisplayName("비동기/논블록킹/블록킹 case 2")
    @Test
    void 가격_조회_비동기_논블록킹_블록킹_호출_테스트2() {
        /*
        (손님) 커피숍에 가서 커피를 주문한다
        (바리스타) 커피 주문을 받는다
        (바리스타) 영수증을 준다
        (바리스타) 커피를 만들기 시작
        (손님) (Non-Blocking) 영수증을 받은다음 자리에가서 논다
        (손님) (Non-Blocking) 자리에서 딴짓하며 계속 논다
        (바리스타) 커피를 만들기 완료
        (손님) (Blocking) 영수증을 들고 커피 받는 곳에 가서 커피가 나왔는지 확인 하러 간다.
        (손님) (Blocking) 커피를 가지고 온다

        join(), get()을 사용하게 되면 Blocking 포인트가 발생
         */

        int expectedPrice = 1100;

        log.info("(손님) 커피숍에 가서 커피를 주문한다");
        CompletableFuture<Integer> future = coffeeComponent.getPriceAsync("latte");
        log.info("(손님) (Non-Blocking) 영수증을 받은다음 자리에가서 논다");

        log.info("(손님) (Non-Blocking) 자리에서 딴짓하며 계속 논다");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("(손님) (Blocking) 영수증을 들고 커피 받는 곳에 가서 커피가 나왔는지 확인 하러 간다.");
        int resultPrice = future.join();
        log.info("(손님) (Blocking) 커피를 가지고 온다");

        assertThat(resultPrice).isEqualTo(expectedPrice);
    }

    @DisplayName("비동기/논블록킹/콜백 case 1")
    @Test
    void 가격_조회_비동기_논블록킹_호출_콜백_반환없음_테스트() {
        /*
        (손님) 커피숍에 가서 커피를 주문한다
        (바리스타) 커피 주문을 받는다
        (바리스타) 영수증을 준다
        (바리스타) 커피를 만들기 시작
        (손님) (Non-Blocking) 영수증을 받은다음 자리에가서 논다
        (손님) (Non-Blocking) 자리에서 딴짓하며 계속 논다
        (바리스타) 커피를 만들기 완료
        (바리스타) 손님에게 커피를 가져다 준다

        콜백을 이용하여 Blocking 포인트 없이 진행
        CompletableFuture thenAccept() 사용
         */

        int expectedPrice = 1100;
        log.info("(손님) 커피숍에 가서 커피를 주문한다");

        CompletableFuture<Void> future = coffeeComponent
                .getPriceAsync("latte")
                .thenAccept(p -> {
                    log.info("(손님) 바리스타가 커피를 가져다 줘서 맛있게 커피를 마신다.");
                    assertThat(p).isEqualTo(expectedPrice);
                });

        log.info("(손님) (Non-Blocking) 영수증을 받은다음 자리에가서 논다");
        log.info("(손님) (Non-Blocking) 자리에서 딴짓하며 계속 논다");


        /*
        아래 구문(assertThat(future.join()).isNull())이 없으면
        main thread가 종료되기 때문에 thenAccept 확인하기 전에 끝남
        그래서 테스트를 위해 main thread가 종료되지 않도록 블록킹으로 대기하기 위한 코드
        future가 complete가 되면 위에 작성한 thenAccept 코드가 실행이 됨
         */

        assertThat(future.join()).isNull();
    }

    @DisplayName("비동기/논블록킹/콜백 case 2")
    @Test
    void 가격_조회_비동기_논블록킹_호출_콜백_반환_테스트1() {
        /*
        (손님) 커피숍에 가서 커피를 주문한다
        (바리스타) 커피 주문을 받는다
        (바리스타) 영수증을 준다
        (바리스타) 커피를 만들기 시작
        (손님) (Non-Blocking) 영수증을 받은다음 자리에가서 논다
        (손님) (Non-Blocking) 자리에서 딴짓하며 계속 논다
        (바리스타) 커피를 만들기 완료
        (바리스타) 손님에게 줄 디저트를 추가로 챙김
        (바리스타) 손님에게 커피와 디저트를 가져다 준다

        콜백을 이용하여 Blocking 포인트 없이 진행
        CompletableFuture thenApply(), thenAccept() 사용
        thenApply(), thenAccept()를 동일 쓰레드에서 실행함
         */

        int expectedPrice =  1200;
        log.info("(손님) 커피숍에 가서 커피를 주문한다");

        CompletableFuture<Void> future = coffeeComponent
                .getPriceAsync("latte")
                .thenApply(p -> {
                    log.info("(바리스타) 손님에게 줄 디저트를 추가로 챙김");
                    return p + 100;
                })
                .thenAccept(p -> {
                    log.info("(바리스타) 손님에게 커피와 디저트를 가져다 준다");
                    assertThat(p).isEqualTo(expectedPrice);
                });
        log.info("(손님) (Non-Blocking) 영수증을 받은다음 자리에가서 논다");
        log.info("(손님) (Non-Blocking) 자리에서 딴짓하며 계속 논다");

        /*
        아래 구문(assertThat(future.join()).isNull())이 없으면
        main thread가 종료되기 때문에 thenAccept 확인하기 전에 끝남
        그래서 테스트를 위해 main thread가 종료되지 않도록 블록킹으로 대기하기 위한 코드
        future가 complete가 되면 위에 작성한 thenAccept 코드가 실행이 됨
         */

        assertThat(future.join()).isNull();
    }

    @DisplayName("비동기/논블록킹/콜백 case 3")
    @Test
    void 가격_조회_비동기_논블록킹_호출_콜백_반환_테스트2() {
        /*
        (손님) 커피숍에 가서 커피를 주문한다
        (바리스타) 커피 주문을 받는다
        (바리스타) 영수증을 준다
        (바리스타) 커피를 만들기 시작
        (손님) (Non-Blocking) 영수증을 받은다음 자리에가서 논다
        (손님) (Non-Blocking) 자리에서 딴짓하며 계속 논다
        (바리스타) 커피를 만들기 완료
        (바리스타) 손님에게 줄 디저트를 추가로 챙김
        (바리스타) 손님에게 커피와 디저트를 가져다 준다

        콜백을 이용하여 Blocking 포인트 없이 진행
        CompletableFuture thenApply(), thenAccept() 사용
        thenApply(), thenAccept()를 다른 쓰레드에서 실행함
         */

        int expectedPrice =  1200;
        Executor executor = Executors.newFixedThreadPool(5);

        log.info("(손님) 커피숍에 가서 커피를 주문한다");

        CompletableFuture<Void> future = coffeeComponent
                .getPriceAsync("latte")
                .thenApplyAsync(p -> {
                    log.info("(바리스타) 손님에게 줄 디저트를 추가로 챙김");
                    return p + 100;
                }, executor)
                .thenAcceptAsync(p -> {
                    log.info("(바리스타) 손님에게 커피와 디저트를 가져다 준다");
                    assertThat(p).isEqualTo(expectedPrice);
                }, executor);
        log.info("(손님) (Non-Blocking) 영수증을 받은다음 자리에가서 논다");
        log.info("(손님) (Non-Blocking) 자리에서 딴짓하며 계속 논다");

        /*
        아래 구문(assertThat(future.join()).isNull())이 없으면
        main thread가 종료되기 때문에 thenAccept 확인하기 전에 끝남
        그래서 테스트를 위해 main thread가 종료되지 않도록 블록킹으로 대기하기 위한 코드
        future가 complete가 되면 위에 작성한 thenAccept 코드가 실행이 됨
         */

        assertThat(future.join()).isNull();
    }

    @Test
    void thenCombine_test() {
        int expectedPrice = 1100 + 1300;

        log.info("(손님) 커피숍에 가서 라떼 커피를 주문한다");
        CompletableFuture<Integer> futureA = coffeeComponent.getPriceAsync("latte");

        log.info("(손님) 커피숍에 가서 모카 커피를 주문한다");
        CompletableFuture<Integer> futureB = coffeeComponent.getPriceAsync("mocha");

        Integer resultPrice = futureA.thenCombine(futureB, Integer::sum).join();

        assertThat(resultPrice).isEqualTo(expectedPrice);
    }

    @Test
    void thenCompose_test() {
        int expectedPrice = (int)(1100 * 0.9);

        CompletableFuture<Integer> futureA = coffeeComponent.getPriceAsync("latte");

        Integer resultPrice = futureA.thenCompose(result -> coffeeComponent.getDiscountPriceAsync(result)).join();

        assertThat(resultPrice).isEqualTo(expectedPrice);
    }

    @Test
    void allOf_test() {
        int expectedPrice = 1100 + 1300 + 900;

        log.info("(손님) 커피숍에 가서 라떼 커피를 주문한다");
        CompletableFuture<Integer> futureA = coffeeComponent.getPriceAsync("latte");

        log.info("(손님) 커피숍에 가서 모카 커피를 주문한다");
        CompletableFuture<Integer> futureB = coffeeComponent.getPriceAsync("mocha");

        log.info("(손님) 커피숍에 가서 아메리카노 커피를 주문한다");
        CompletableFuture<Integer> futureC = coffeeComponent.getPriceAsync("americano");

        List<CompletableFuture<Integer>> completableFutureList = Arrays.asList(futureA, futureB, futureC);

        Integer resultPrice = CompletableFuture.allOf(futureA, futureB, futureC)
                .thenApply(Void -> completableFutureList.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()))
                .join()
                .stream()
                .reduce(0, Integer::sum);

        assertThat(resultPrice).isEqualTo(expectedPrice);
    }

}