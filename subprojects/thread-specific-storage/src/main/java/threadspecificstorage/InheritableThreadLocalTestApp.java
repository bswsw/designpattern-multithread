package threadspecificstorage;

public class InheritableThreadLocalTestApp {

    public static void main(String[] args) {
        var threadLocal = new InheritableThreadLocal<Integer>();

        threadLocal.set(1);

        System.out.println("[main thread] value: " + threadLocal.get());

        new Thread(() -> System.out.println("[other thread] value: " + threadLocal.get())).start();
    }
}
