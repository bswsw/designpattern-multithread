package twophasetermination;

import lombok.Getter;

public class CountupThread extends Thread {

    private long counter = 0;

    @Getter
    private volatile boolean shutdownRequested = false;

    public void shutdownRequest() {
        shutdownRequested = true;
        interrupt();
    }

    private void doWork() throws InterruptedException {
        counter++;
        System.out.println("doWork: counter = " + counter);
        Thread.sleep(500);
    }

    private void doShutdown() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        System.out.println("doShutdown: counter = " + counter);
    }

    @Override
    public void run() {
        try {
            while (!isShutdownRequested()) {
                doWork();
            }
        } catch (InterruptedException e) {

        } finally {
            doShutdown();
        }
    }
}
