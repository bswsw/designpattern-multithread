package singlethread.atomic;

public class AtomicSampleApp {

    public static void main(String[] args) throws InterruptedException {
        var i = new FakeAtomicInteger();

        new Thread(() -> i.getAndIncrement()).start();
        new Thread(() -> i.getAndIncrement()).start();

        Thread.sleep(3000);

        System.out.println(i.getAndIncrement());
    }
}
