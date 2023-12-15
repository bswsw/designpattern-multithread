package pubsub.sample2;

public class Sample2App {

    public static void main(String[] args) throws InterruptedException {
        var th1 = new TestThread();
        th1.start();

        System.out.println("Thread.currentThread(): " + Thread.currentThread());
        System.out.println("th1.current(): " + th1.current());
        System.out.println("===================================");

//        Thread.sleep(5000);
        System.out.println("init: " + th1.isInterrupted());

        th1.interrupt();
        System.out.println("th1.interrupt(): " + th1.isInterrupted());

        Thread.interrupted();
        System.out.println("Thread.interrupted(): " + th1.isInterrupted());

//        th1.dointerrupted();
//        System.out.println("th1.dointerrupted(): " + th1.isInterrupted());

    }
}
