package com.ydskingdom.batch.testJob6;

import com.ydskingdom.batch.testJob5.DummyData;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.List;

public class Job6ItemReader implements ItemReader<String> {

    private List<String> dummyData;
    private int index = -1;

    public Job6ItemReader() {
        this.index = 0;
        this.dummyData = DummyData.getDummyData();
    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String value;

        if (index < dummyData.size()) {
            value = dummyData.get(index);
            index++;
        } else{
            index = 0;
            value = null;
        }

        System.out.println("ItemReader Read item : " + value);
        return value;
    }
}
