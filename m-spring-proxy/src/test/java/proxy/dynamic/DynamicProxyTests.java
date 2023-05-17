package proxy.dynamic;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

public class DynamicProxyTests {
    @Test
    public void dynamicProxyExample() {
        //Create the proxy
        //동적으로 프록시 생성
        // rabbit
        Animal rabbit = (Animal) Proxy.newProxyInstance(Animal.class.getClassLoader(), new Class[]{Animal.class}, new AnimalProxyHandler(new Rabbit()));
        // tiger
        Animal tiger = (Animal) Proxy.newProxyInstance(Animal.class.getClassLoader(), new Class[]{Animal.class}, new AnimalProxyHandler(new Tiger()));

        // Invoke the target instance method through the proxy
        rabbit.eat();
        System.out.println();
        rabbit.drink();
        System.out.println();

        tiger.eat();
        System.out.println();
        tiger.drink();
        System.out.println();
    }
}
