package com.ydskingdom.batch.testJob6;

import org.springframework.batch.item.ItemProcessor;

public class Job6ItemProcessor implements ItemProcessor<String, String> {
    @Override
    public String process(String item) throws Exception {
        try {
            Thread.sleep(3000);
            //ItemProcess 비즈니스 로직 영역
            System.out.println("ItemProcess input -> output : " + item + " -> " + item);
        } catch (Exception e) {
        }

        return item;
    }
}
