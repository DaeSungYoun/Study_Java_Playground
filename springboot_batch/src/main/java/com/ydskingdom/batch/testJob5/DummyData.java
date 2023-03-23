package com.ydskingdom.batch.testJob5;

import java.util.ArrayList;
import java.util.List;

public class DummyData {
    public static List<String> getDummyData() {
        List<String> items = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            items.add(String.valueOf(i));
        }
        return items;
    }
}
