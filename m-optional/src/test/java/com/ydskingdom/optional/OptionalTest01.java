package com.ydskingdom.optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OptionalTest01 {

    /*
    .empty()
    .of()
    .ofNullable()
    .get()
    .isPresent()
    .isEmpty()
    .ifPresent()
        - ifPresent()는 값이 있으면 내부 로직 실행, 값이 없으면 실행 안함
        -
    .ifPresentOrElse()
    .filter()
    .map()
    .flatMap()
    .or()
    .stream()
    .orElse()
    .orElseGet()
    .orElseThrow()
    .equals()
    .hashCode()
    .toString()
     */

    @DisplayName("empty() 테스트")
    @Test
    void optional_empty_test_01() {
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

    @Test
    void optional_of_test_01() {
        String name = "test";
        Optional<String> opt = Optional.of(name);
        System.out.println("opt = " + opt);
        System.out.println("opt.get() = " + opt.get());
        assertThat(opt.toString()).isEqualTo("Optional[test]");
    }

    @Test
    void optional_of_test_02() {
        //.of()를 사용할 때 null값이 들어가면 NullPointException이 발생함
        String name = null;
        Assertions.assertThrows(NullPointerException.class, () -> {
            Optional<String> opt = Optional.of(name);
        });
    }

    @Test
    void optional_ofNullable_test_01() {
        //ofNullable()은 null도 가능함
        String name = "test";
        Optional<String> opt = Optional.ofNullable(name);
//        assertEquals("Optional[test]", opt.toString());
    }

    @Test
    void optional_ofNullable_test_02() {
        //ofNullable()은 null도 가능함
        String name = null;
        Optional<String> opt = Optional.ofNullable(name);
//        assertEquals("Optional.empty", opt.toString());
    }

    @Test
    void optional_ifPresent_test_01() {
        Optional<String> opt = Optional.of("test");

        opt.ifPresent(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s.length());
            }
        });

        opt.ifPresent(name -> System.out.println(name.length()));
    }

    @Test
    void optional_ifPresent_test() {
        Optional<String> notEmpty = Optional.of("hi");
        Optional<Object> empty = Optional.empty();

        notEmpty.ifPresent(v -> {
            System.out.println("notEmpty는 값이 있으니 출력 됨!");
        });

        empty.ifPresent(v -> {
            System.out.println("empty는 값이 없어서 출력 안됨");
        });
    }
}
