package proxy.dynamic;

public class Tiger implements Animal {
    @Override
    public void eat() {
        System.out.println("호랑이가 음식을 먹습니다");
    }

    @Override
    public void drink() {
        System.out.println("호랑이가 물을 마십니다");
    }
}
