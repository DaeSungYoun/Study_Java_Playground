package com.ydskingdom.json.test01;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class JsonConvertTest {

    @Test
    void ddddd() throws JsonProcessingException {
        User a = new User("AAA", "BB", "CCC", "DDD");
        System.out.println("Object : " + a);

        ObjectMapper om = new ObjectMapper();
        String s = om.writeValueAsString(a);
        System.out.println("Object to Json : " + s);
    }

}