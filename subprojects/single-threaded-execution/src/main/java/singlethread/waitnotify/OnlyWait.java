package singlethread.waitnotify;

public class OnlyWait {

    synchronized void methodWait(String name) {
        try {
            System.out.println("[OnlyWait] wait: " + name);
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("wakeup: " + name);

    }

}
