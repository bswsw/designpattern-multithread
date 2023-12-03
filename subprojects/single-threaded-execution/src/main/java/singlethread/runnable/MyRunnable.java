package singlethread.runnable;

public class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("MyRunnable current thread : " + Thread.currentThread());
    }
}
