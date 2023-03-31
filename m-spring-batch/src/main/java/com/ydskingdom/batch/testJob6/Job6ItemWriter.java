package com.ydskingdom.batch.testJob6;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class Job6ItemWriter implements ItemWriter<String> {
    @Override
    public void write(List<? extends String> items) throws Exception {
        System.out.println("ItemWriter input items + " + items);
    }
}
