package hello.core.singleton;

public class SingletonService {

    // 컴파일 시에 이미 객체 레퍼런스를 필드로써 갖고있다.
    private static final SingletonService instance = new SingletonService();

    private SingletonService() {
    }

    public static SingletonService getInstance() {
        return instance;
    }

    public void logic() {
        System.out.println("싱글톤 객체 호출");
    }
}
