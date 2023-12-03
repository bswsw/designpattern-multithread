package singlethread.waitnotify;

public class WaitNotifySampleApp {
    public static void main(String[] args) throws InterruptedException {
        // 1
        System.out.println("1. 같은 클래스, 다른 인스턴스 notify");

        var waitNotify = new WaitNotify();
        new Thread(() -> waitNotify.methodWait("name1")).start();
        new Thread(() -> waitNotify.methodWait("name2")).start();

        Thread.sleep(1000);

        new Thread(() -> waitNotify.methodNotifyAll()).start();

        // 2
        System.out.println("2. 같은 클래스, 다른 인스턴스 notify");

        var waitNotify3 = new WaitNotify();
        new Thread(() -> waitNotify3.methodWait("name3")).start();

        var waitNotify4 = new WaitNotify();
        new Thread(() -> waitNotify4.methodWait("name4")).start();

        Thread.sleep(1000);

        new Thread(() -> waitNotify3.methodNotifyAll()).start();

        // 3
        System.out.println("2. 다른 클래스, 다른 인스턴스 notify");

        var onlyWait2 = new OnlyWait();
        new Thread(() -> onlyWait2.methodWait("name1")).start();

        var waitNotify2 = new WaitNotify();
        new Thread(() -> waitNotify2.methodWait("name1")).start();

        Thread.sleep(1000);

        new Thread(() -> waitNotify2.methodNotifyAll()).start();
    }
}
