package com.ydskingdom.batch.testJob5;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class Job5ItemWriter implements ItemWriter<String> {
    @Override
    public void write(List<? extends String> items) throws Exception {
        System.out.println("ItemWriter input items + " + items);
    }
}
