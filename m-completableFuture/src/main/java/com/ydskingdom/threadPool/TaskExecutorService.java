package com.ydskingdom.threadPool;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.concurrent.CompletableFuture;

@EnableAsync
@Service
public class TaskExecutorService {

    @Async("taskExecutor1")
    public CompletableFuture<String> encryptData(String data) {
        String encryptedData = encrypt(data);
        return CompletableFuture.completedFuture(encryptedData);
    }

    @Async("taskExecutor2")
    public CompletableFuture<String> getImageData() {
        byte[] imageData = downloadImage();
        String encodedImageData = Base64.getEncoder().encodeToString(imageData);
        return CompletableFuture.completedFuture(encodedImageData);
    }

    private String encrypt(String data) {
        return data;
    }

    private byte[] downloadImage() {
        return null;
    }
}
