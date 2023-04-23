package com.ydskingdom.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedisStringTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void redis_STRING_타입_테스트() {
        /*
        redisTemplate.opsForValue().append(K key, String value)
        redisTemplate.opsForValue().decrement(K key)
        redisTemplate.opsForValue().decrement(K key, long delta)
        redisTemplate.opsForValue().get(Obejct key)
        redisTemplate.opsForValue().get(K key, long start, long end)
        redisTemplate.opsForValue().getAndDelete(K key)
        redisTemplate.opsForValue().getAndExprie(K key, long timeout, TimeUnit unit)


         */
        // given
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = "stringKey";

        // when
        valueOperations.set(key, "hello");

        DataType type = redisTemplate.type(key);

        // then
        System.out.println("type.code() : " + type.code());
        System.out.println("type.name() : " + type.name());
        System.out.println("type : " + type);

        assertThat(type.code()).isEqualTo("string");
        assertThat(type.name()).isEqualTo("STRING");
        assertThat(type).isEqualTo(DataType.STRING);
    }

    @Test
    void testStrings() {
        // given
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = "stringKey";

        // when
        valueOperations.set(key, "hello");

        // then
        String value = valueOperations.get(key);
        assertThat(value).isEqualTo("hello");
    }
}
