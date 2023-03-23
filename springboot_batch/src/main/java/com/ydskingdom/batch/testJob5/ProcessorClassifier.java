package com.ydskingdom.batch.testJob5;

import lombok.Setter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

import java.util.Map;

@Setter
public class ProcessorClassifier<C, T> implements Classifier<C, T> {
    private Map<Integer, ItemProcessor<String, String>> paramMap;

    @Override
    public T classify(C classifiable) {
        String key = (String) classifiable;
        if (Integer.parseInt(key) % 2 == 1) {
            return (T)paramMap.get(0);
        } else {
            return (T)paramMap.get(1);
        }
    }
}
