package com.ydskingdom.threadPool;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;

@SpringBootTest
class TaskExecutorServiceTest {

    @Autowired
    private TaskExecutorService taskExecutorService;

    @Test
    void tttt() {
        CompletableFuture<String> future = taskExecutorService.encryptData("hi");
        String data = future.join();
    }
}