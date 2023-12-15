package pubsub.chef;

import pubsub.sample2.TestThread;

public class PubsubChefApp {
    public static void main(String[] args) throws InterruptedException {
        Table table = new Table(3);

        var maker = new MakerThread("maker-1", table, 31415);
        maker.start();

        Thread.sleep(1000);

        System.out.println("interrupt: " + maker.isInterrupted());

        maker.interrupt();
        System.out.println("maker.interrupt(): " + maker.isInterrupted());

        maker.doInterrupted();
        System.out.println("maker.doInterrupted(): " + maker.isInterrupted());

        Thread.interrupted();
        System.out.println("maker.doInterrupted(): " + maker.isInterrupted());

        maker.interrupt();

        new MakerThread("maker-2", table, 92653).start();
        new MakerThread("maker-3", table, 58979).start();

//        new EaterThread("eater-1", table, 32384).start();
//        new EaterThread("eater-2", table, 62643).start();
//        new EaterThread("eater-3", table, 38327).start();
    }
}
