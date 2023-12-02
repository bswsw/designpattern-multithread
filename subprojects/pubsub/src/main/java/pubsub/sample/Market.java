package pubsub.sample;

public class Market {

    private final static int MIN = 0;
    private final static int MAX = 3;

    private int count = 0;

    synchronized public void add() {
        System.out.println("추가전 : " + count);

        while (count >= MAX) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        count++;
        notifyAll();
        System.out.println("추가후 : " + count);
    }

    synchronized public void remove() {
        System.out.println("삭제전 : " + count);

        while (count <= MIN) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        count--;
        notifyAll();
        System.out.println("삭제후 : " + count);
    }

}
