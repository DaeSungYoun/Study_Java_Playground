package com.ydskingdom.batch.testJob5;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor1 implements ItemProcessor<String, String> {
    @Override
    public String process(String item) throws Exception {
        System.out.println("CustomItemProcessor1 -> " + item);
        return item + "a";
    }
}
