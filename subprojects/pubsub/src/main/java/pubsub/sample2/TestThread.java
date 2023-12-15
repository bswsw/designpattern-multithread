package pubsub.sample2;

public class TestThread extends Thread {

    public TestThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println("run: " + Thread.currentThread());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("run catch: " + isInterrupted());
            interrupted();
        }

        interrupted();
        System.out.println("run2: " + Thread.currentThread());
    }

    public Thread current() {
        return Thread.currentThread();
    }

}
