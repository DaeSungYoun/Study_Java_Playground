package com.ydskingdom.optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.*;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OptionalTest01 {

    /*
    # 중간 처리 메서드
        .empty()
            - null optional 생성
            - optional_null_check()
        .of()
            - 값이 있는 optional 생성
            - optional_of_test()
        .ofNullable()
            - null 가능성이 있는 optional 생성
            - optional_ofNullable_test()
        .filter()
            - Predicate의 결과가 treu이면 Optional 자신을 리턴, false이면 Empty Optional(null)을 리턴
            - optional_filter_test()
        .map()
            - Optional 객체가 값을 갖고 있으면, map 메서드로 넘겨진 함수를 통해 값의 형태를 변경한다.
            - Optional 객체가 비어있는 경우 연산을 수행하지 않는다.
            - optional_map_test()
        .flatMap()
        .or()
        .stream()
    # 종단 처리 메서드
        .isPresent()
            - optional에 값이 있는지 없는지 체크
            - optional_null_check()
        .isEmpty()
            - optional에 값이 있는지 없는지 체크
            - optional_empty_test()
        .ifPresent()
            - optional에 값이 있는지 없는지 체크
            - ifPresent()는 값이 있으면 내부 로직 실행, 값이 없으면 실행 안함
            - optional_ifPresent_test()
        .ifPresentOrElse()
            - optional에 값이 있는지 없는지 체크
            - 제일 깔끔한 느낌? 값이 있을 떄, 없을 때 모두 처리 가능
            - optional_ifPresent_test()
            - optional_ifPresentOrElse_test()
        .get()
            - 데이터 꺼내기
        .orElse()
            - Optional 데이터가 있든 없든 orElse() 실행됨
        .orElseGet()
            - Optional 데이터가 null일 경우에만 orElseGet() 메서드 실행됨
        .orElseThrow()
            - Optional 데이터가 null일 경우에만 Exception 동작

    .equals()
    .hashCode()
    .toString()
     */

    @DisplayName("empty 테스트")
    @Test
    void optional_empty_test() {
        Optional<Object> empty = Optional.empty();

        // case 01 ->
        System.out.println("empty = " + empty);
        assertThat(empty.toString()).isEqualTo("Optional.empty");

        // case 02 -> Optional.empty()에서 get()으로 값을 꺼내려고하면 NoSuchElementException 발생
        assertThatThrownBy(() -> {
            System.out.println("empty = " + empty.get());
        }).isInstanceOf(NoSuchElementException.class)
                .hasMessage("No value present");

        // case 03 ->
        assertThat(empty.isPresent()).isEqualTo(false);

        // case 04 ->
        assertThat(empty.isEmpty()).isEqualTo(true);
    }

    @DisplayName("isPresent 테스트")
    @Test
    void optional_null_check() {
        /*
        isPresent() -> 없으면 false, 있으면 true
        isEmpty() -> 없으면 ture, 있으면 false
         */

        Optional<String> optional1 = Optional.empty();
        System.out.println(optional1.isPresent());
        System.out.println(optional1.isEmpty());

        Optional<String> optional2 = Optional.of("coco");
        System.out.println(optional2.isPresent());
        System.out.println(optional2.isEmpty());

        Optional<String> optional3 = Optional.ofNullable(null);
        System.out.println(optional3.isPresent());
        System.out.println(optional3.isEmpty());

        Optional<String> optional4 = Optional.ofNullable("hi");
        System.out.println(optional4.isPresent());
        System.out.println(optional4.isEmpty());
    }

    @DisplayName("of 테스트")
    @Test
    void optional_of_test() {
        //.of()를 사용할 때 null값이 들어가면 NullPointException이 발생함
        String name = null;
        Assertions.assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Optional<String> opt = Optional.of(name);
            }
        });

        //null 값이 아닐 경우에 of를 사용해야함
        String value = "test";
        Optional<String> opt = Optional.of(value);
        System.out.println("opt = " + opt);
        System.out.println("opt.get() = " + opt.get());
        assertThat(opt.toString()).isEqualTo("Optional[test]");
    }

    @DisplayName("ofNullable 테스트")
    @Test
    void optional_ofNullable_test() {
        //ofNullable()은 null 가능성이 있는 경우 사용
        String name = null;
        Optional<String> opt = Optional.ofNullable(name);

        //java 11 미만인 경우 isPresent() 사용
        if (opt.isPresent()) {
            System.out.println(opt.get());
        } else {
            //null인 객체를 바로 get()하게 되면 NosuchElementException 동작함
            assertThatThrownBy(() -> {
                System.out.println("empty = " + opt.get());
            }).isInstanceOf(NoSuchElementException.class);
        }

        //java 11 이상인 경우 ifPresent() 사용
        opt.ifPresent(v -> System.out.println(v));
    }

    @DisplayName("ifPresent 테스트")
    @Test
    void optional_ifPresent_test() {
        Optional<String> notEmpty = Optional.of("hi");
        Optional<Object> empty = Optional.empty();

        notEmpty.ifPresent(v -> System.out.println("notEmpty는 값이 있으니 출력 됨!"));
        notEmpty.ifPresent(v -> System.out.println(v.length()));

        empty.ifPresent(v -> System.out.println("empty는 값이 없어서 출력 안됨"));
        empty.ifPresentOrElse(v -> System.out.println("empty는 값이 없어서 출력 안됨"), () -> System.out.println("요렇게 하면 출력 됨"));
    }

    @DisplayName("ifPresentOrElse 테스트")
    @Test
    void optional_ifPresentOrElse_test() {
        /*
        ifPresentOrElse() 사용 시
        첫번째 인자 값 Consumer에는 null이 아닐 경우 동작함
        두번째 인자 값 Runnable에는 null일 경우 동작함

        ifPresentOrElse()는 Java 11버전 이후부터 사용 가능
        */

        String value = null;
//        String value = "hi";
        Optional<String> opt = Optional.ofNullable(value);
        opt.ifPresentOrElse(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("s = " + s);
            }
        }, new Runnable() {
            @Override
            public void run() {
                System.out.println("여긴 뭐지");
            }
        });
    }

    @DisplayName("filter 테스트")
    @Test
    void optional_filter_test() {
        String value = "hi";
        Optional<String> opt = Optional.ofNullable(value);
        opt.filter(s -> s.equals("hi")); //true이면 opt 자신을 그대로 반환, false이면 empty optional 반환
    }

    @DisplayName("map 테스트")
    @Test
    void optional_map_test() {
        String value = "11";
        Optional<Integer> integer = Optional.ofNullable(value).map(v -> Integer.parseInt(value));
        System.out.println(integer);
    }

    @DisplayName("or 테스트")
    @Test
    void optional_or_test() {
        Optional<String> or = Optional.ofNullable("Hi")
                .filter(value -> value.length() > 3) // `filter` 조건에 걸러짐. 빈 Optional 반환
                .or(() -> Optional.of("Hello"));// 값이 없으므로 대체 Optional[Hello] 반환
        System.out.println("or = " + or);
    }

    @DisplayName("orElse 테스트")
    @Test
    void optional_orElse_test() {
        String str = null;

        // "hi" 반환
        String hi = Optional.ofNullable(str).orElse("hi");
        System.out.println("hi = " + hi);
    }

    @DisplayName("orElseGet 테스트")
    @Test
    void optional_orElseGet_test() {
        String user = "user1";

        String s = Optional.ofNullable(user).orElse(makeDefaultUser());
        System.out.println("s = " + s);

        System.out.println("============================");

        String s1 = Optional.ofNullable(user).orElseGet(() -> makeDefaultUser());
        System.out.println("s1 = " + s1);
    }

    private String makeDefaultUser() {
        System.out.println("makeDefaultUser() method called!");
        return "user2";
    }

    @DisplayName("orElseThrow 테스트")
    @Test
    void optional_orElseThrow_test() throws Throwable {
        String value = null;

        //Java 10에서는 Exception을 설정하지 않으면 NoSuchElementException이 기본값으로 설정된다
//        Optional.ofNullable(value).orElseThrow();

        //Exception을 설정하여 사용 가능
//        Optional.ofNullable(value).orElseThrow(RuntimeException::new);
    }
}
