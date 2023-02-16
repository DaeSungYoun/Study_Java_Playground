package com.ydskingdom.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Test
    void testSet() {
        // given
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        String key = "setKey";

        Set<String> members1 = setOperations.members(key);
        Iterator<String> iterator = members1.iterator();

        while (iterator.hasNext()) {
            setOperations.remove(key, iterator.next());
        }

        // when
        setOperations.add(key, "h", "e", "l", "l", "o");
        setOperations.add(key, "1");
        setOperations.add(key, "2");
        setOperations.add(key, "3");
        setOperations.add(key, "4");
        setOperations.add(key, "5");
        setOperations.add(key, "6");

        System.out.println("member : " + setOperations.isMember(key, "h"));
        System.out.println("member : " + setOperations.isMember(key, "11"));

        // then
        Set<String> members = setOperations.members(key);
        Long size = setOperations.size(key);

        assertThat(members).containsOnly("h", "e", "l", "o", "1", "2", "3", "4", "5", "6");
        assertThat(size).isEqualTo(10);
    }

    @Test
    void testHash() {
        // given
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        String key = "hashKey";

        // when
        hashOperations.put(key, "hello", "world");

        // then
        Object value = hashOperations.get(key, "hello");
        assertThat(value).isEqualTo("world");

        Map<Object, Object> entries = hashOperations.entries(key);
        assertThat(entries.keySet()).containsExactly("hello");
        assertThat(entries.values()).containsExactly("world");

        Long size = hashOperations.size(key);
        assertThat(size).isEqualTo(entries.size());
    }
}
