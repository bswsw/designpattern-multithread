package workerthread.example;

import java.util.Random;

public record Request(String name, int number) implements Runnable {

    private static final Random random = new Random();

    public void execute() {
        System.out.println(Thread.currentThread().getName() + " executes " + this);

        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {

        }
    }

    @Override
    public void run() {
        execute();
    }
}
