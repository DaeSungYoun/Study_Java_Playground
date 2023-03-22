package com.ydskingdom.batch.testJob5;

import org.springframework.batch.item.ItemProcessor;

public class Job5ItemProcessor implements ItemProcessor<String, String> {
    private int retryCount = 0;
    private final int maxRetries = 2;

    @Override
    public String process(String item) throws Exception {
        try {
            //ItemProcess 비즈니스 로직 영역
            System.out.println("ItemProcess input -> output : " + item + " -> " + item);
            if (item.equals("21")) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            if (++retryCount > maxRetries) {
                System.out.println("ItemProcess Retry " + (retryCount - 1) + "회 초과하여 DB에 해당 값 저장 후 ItemProcess 진행 합니다");
                retryCount = 0;
                return null;
            }
            return process(item);
        }

        retryCount = 0;
        return item;
    }
}
