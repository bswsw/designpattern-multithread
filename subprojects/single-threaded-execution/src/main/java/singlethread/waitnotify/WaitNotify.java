package singlethread.waitnotify;

public class WaitNotify {

    synchronized void methodWait(String name) {
        try {
            System.out.println("[WaitNotify] wait: " + name);
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("[WaitNotify] wakeup: " + name);

    }

    synchronized void methodNotifyAll() {
        System.out.println("[WaitNotify] notifyAll");
        notifyAll();
    }

    synchronized void methodNotify() {
        System.out.println("[WaitNotify] notify");
        notify();
    }
}
