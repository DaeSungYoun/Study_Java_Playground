package com.ydskingdom.redis;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void redisTemplate_테스트() {
        redisTemplate.opsForValue();
        redisTemplate.opsForList();
        redisTemplate.opsForSet();
        redisTemplate.opsForZSet();
        redisTemplate.opsForHash();
        redisTemplate.delete("");
        redisTemplate.delete(Lists.newArrayList("1", "2"));
        redisTemplate.countExistingKeys(Lists.newArrayList("1", "2"));
    }

    /*
    요청한 키 값들 중에 유효한 키 개수 반환
     */
    @Test
    void 키_유무_확인_테스트() {
        // given
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = "stringKey";

        // when
        valueOperations.set(key, "hello");

        Long countExistingKeys = redisTemplate.countExistingKeys(Lists.newArrayList(key));

        // then
        String value = valueOperations.get(key);
        assertThat(value).isEqualTo("hello");

        System.out.println("countExistingKeys : " + countExistingKeys);
        assertThat(countExistingKeys).isEqualTo(1);
    }
}
