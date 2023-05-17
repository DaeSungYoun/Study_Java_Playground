package proxy.dynamic;

public class Rabbit implements Animal {
    @Override
    public void eat() {
        System.out.println("토끼가 음식을 먹습니다");
    }

    @Override
    public void drink() {
        System.out.println("토끼가 물을 마십니다");
    }
}
