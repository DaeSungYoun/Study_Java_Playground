package com.ydskingdom.batch.testJob5;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor2 implements ItemProcessor<String, String> {
    @Override
    public String process(String item) throws Exception {
        System.out.println("CustomItemProcessor2 -> " + item);
        Thread.sleep(2000);
        return item + "b";
    }
}
