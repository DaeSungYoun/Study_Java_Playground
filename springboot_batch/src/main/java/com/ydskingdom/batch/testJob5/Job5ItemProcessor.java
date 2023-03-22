package com.ydskingdom.batch.testJob5;

import org.springframework.batch.item.ItemProcessor;

public class Job5ItemProcessor implements ItemProcessor<String, String> {
    @Override
    public String process(String item) throws Exception {
        String returnItem = item + item;
        System.out.println("ItemProcess input -> output : " + item + " -> " + returnItem);
        return returnItem;
    }
}
