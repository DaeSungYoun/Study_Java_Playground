package com.ydskingdom.redis;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import java.util.Iterator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedisSetTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void redis_SET_타입_테스트() {
        /*
        redisTemplate.opsForSet().add(K key, V...value)                     : key + value 추가
        redisTemplate.opsForSet().isMember(K key, Object o)                 : key 값의 value가 있는지 체크
        redisTemplate.opsForSet().isMember(K key, Object ... o)             : key 값의 value가 있는지 체크
        redisTemplate.opsForSet().members(K key)                            : key 값의 모든 value 가져오기
        redisTemplate.opsForSet().move(K key, V value, K destKey)           :
        redisTemplate.opsForSet().pop(K key)                                :
        redisTemplate.opsForSet().pop(K key, long count)                    :
        redisTemplate.opsForSet().randomMember(K key)                       :
        redisTemplate.opsForSet().randomMember(K key, long count)           :
        redisTemplate.opsForSet().remove(key, ...value)                     : key 값의 value 삭제
        redisTemplate.opsForSet().scan(K key, ScanOptions options)          :
        redisTemplate.opsForSet().size(K key)                               :
        redisTemplate.opsForSet().union(Collection <K> keys)                :
        redisTemplate.opsForSet().union(K key, Collection <K> otherKeys)    :
        redisTemplate.opsForSet().union(K key, K otherKey)                  :
         */

        // given
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();

        System.out.println("============================================");

        //key 값 설정
        String aKey = "a";

        //key 값에 value 설정
        setOperations.add(aKey, "1");
        System.out.println("aKey Size : " + setOperations.size(aKey));

        //key 값의 value 가져오기
        setOperations.members(aKey);
        System.out.println("aKeyValue = " + setOperations.members(aKey));

        //key 값의 value가 있는지 체크
        Boolean aKeyIsMember = setOperations.isMember(aKey, "1");
        System.out.println("aKeyIsMember : " + aKeyIsMember);

        Boolean aKeyIsMember2 = setOperations.isMember(aKey, "2");
        System.out.println("aKeyIsMember2 : " + aKeyIsMember2);

        //key 값의 value 삭제
        setOperations.remove(aKey, "1");
        System.out.println("remove -> aKey Size : " + setOperations.size(aKey));
        System.out.println("remove -> aKeyValue = " + setOperations.members(aKey));

        System.out.println("============================================");

        //key 값 설정
        String bKey = "b";

        //key 값에 value 설정
        setOperations.add(bKey, "2", "3");

        //key 값의 value 가져오기
        Set<String> bKeyValue = setOperations.members(bKey);

        //key 값의 value가 있는지 체크
        Boolean bKeyIsMember = setOperations.isMember(bKey, "1");
        Boolean bKeyIsMember2 = setOperations.isMember(bKey, "2");

        System.out.println("bKey Size : " + setOperations.size(bKey));
        System.out.println("bKeyValue = " + bKeyValue);
        System.out.println("bKeyIsMember : " + bKeyIsMember);
        System.out.println("bKeyIsMember2 : " + bKeyIsMember2);

        System.out.println("============================================");
        Long countExistingKeys = redisTemplate.countExistingKeys(Lists.newArrayList(aKey, bKey, "c"));
        System.out.println("countExistingKeys = " + countExistingKeys);




        //key 삭제
        redisTemplate.delete(aKey);


        assertThat(countExistingKeys).isEqualTo(2);
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
}
