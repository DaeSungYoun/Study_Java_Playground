package com.ydskingdom.thread.test02;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCase02 {
    public static void main(String[] args) {
        Runnable task = () -> {
            int sum = 0;
            for (int index = 0; index < 10; index++) {
                sum += index;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " -> " + sum);
            }
            System.out.println(Thread.currentThread().getName() + " -> 최종 합 : " + sum);
        };

        int threadCnt = 50;
        ExecutorService service = Executors.newCachedThreadPool();
//        ExecutorService service = Executors.newFixedThreadPool(threadCnt);
        System.out.println(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < threadCnt; i++) {
            service.execute(task);
        }

        service.shutdown();
    }
}
