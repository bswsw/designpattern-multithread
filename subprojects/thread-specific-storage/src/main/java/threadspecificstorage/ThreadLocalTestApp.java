package threadspecificstorage;

import java.util.Random;

public class ThreadLocalTestApp {
    public static void main(String[] args) {
        var threadLocal = ThreadLocal.withInitial(() -> new Random().nextInt());

        threadLocal.set(1);

        System.out.println("A: " + threadLocal.get());

        new Thread(() -> System.out.println("B: " + threadLocal.get())).start();

        new Thread(() -> System.out.println("C: " + threadLocal.get())).start();
    }
}
