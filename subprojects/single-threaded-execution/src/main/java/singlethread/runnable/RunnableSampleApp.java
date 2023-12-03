package singlethread.runnable;

public class RunnableSampleApp {

    public static void main(String[] args) {
var myThread = new MyThread("my-thread");

myThread.run();
myThread.start();

//        var myRunnable = new MyRunnable();
//        myRunnable.run();
//
//        var myRunnableThread = new Thread(myRunnable, "my-runnable-thread");
//        myRunnableThread.run();
//        myRunnableThread.start();
//
//        var factory = Executors.defaultThreadFactory();
//        factory.newThread(myRunnable).start();
//
//        var factory2 = Executors.defaultThreadFactory();
//        factory2.newThread(myRunnable).start();
//        factory2.newThread(myRunnable).start();
//        factory2.newThread(myRunnable).start();
//        factory2.newThread(myRunnable).start();
//        factory2.newThread(myRunnable).start();
//        factory2.newThread(myRunnable).start();
//        factory2.newThread(myRunnable).start();
    }
}

