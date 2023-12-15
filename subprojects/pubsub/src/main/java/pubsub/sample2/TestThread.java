package pubsub.sample2;

public class TestThread extends Thread {

    @Override
    public void run() {
        System.out.println("run: " + Thread.currentThread());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("catch: " + interrupted());
        }
    }

    public Thread current() {
        return Thread.currentThread();
    }

    public boolean dointerrupted() {
        return Thread.interrupted();
    }
}
