package com.ydskingdom.thread;

import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TestCase01 {

    /*
    병렬처리를 위해 ParallelStream()을 사용할 때 쓰레드 풀을 별도 설정하지 않으면
    공용 스레드 풀이라고 하는 ForkJoinPool.commonPool()을 사용하게 된다.
    ForkJoinPool.commonPool()은 현재 속해있는 해당 JVM내에서 동작하는 애플리케이션들에게 영향을 줄 수 있다.
    (JVM과 애플리케이션이 1:1 관계라면 본인 애플리케이션에서만 사용되는 상황, 1개의 JVM에 여러개의 애플리케이션이 있으면 모든 애플리케이션의 공용 스레드 풀을 사용하는 상황)
    FormJoinPool.commonPool()의 기본 설정은 해당 시스템에서 사용 가능한 프로세서의 수를 기준으로 최대 스레드 수를 결정한다
     */
    @Test
    void test_01() {
        List<String> nameList = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"));

        nameList.parallelStream()
                .map(name -> new Person(name))
                .forEach(person -> person.findAge(person.getName()));
    }

    @Getter
    public static class Person{
        private String name;
        private int age;

        public Person(String name) {
            this.name = name;
        }

        public void findAge(String name) {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(this.getName());
        }
    }
}