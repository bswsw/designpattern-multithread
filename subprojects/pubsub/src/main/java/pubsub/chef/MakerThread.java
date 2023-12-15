package pubsub.chef;

import java.util.Random;

public class MakerThread extends Thread {

    private final Random random;
    private final Table table;
    private static int id = 0;

    public MakerThread(String name, Table table, int seed) {
        super(name);
        this.table = table;
        this.random = new Random(seed);
    }

    public void doInterrupted() {
        Thread.interrupted();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(random.nextInt(1000));
                String cake = "[ Cake No." + nextId() + " by " + getName() + " ]";
                System.out.println(cake);
                table.put(cake);
            }
        } catch (InterruptedException e) {
            System.err.println("InterruptedException: " + e.getMessage());
//            throw new RuntimeException(e);
        }
    }

    private static synchronized int nextId() {
        return id++;
    }
}
