package singlethread.runnable;

public class MyThread extends Thread {

    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println("MyThread current thread : " + Thread.currentThread());
    }
}
