package workerthread.example;

import java.util.Random;
import java.util.concurrent.ExecutorService;

public class ClientThread2 extends Thread {

    private final ExecutorService executorService;

    private static final Random random = new Random();

    public ClientThread2(String name, ExecutorService executorService) {
        super(name);
        this.executorService = executorService;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i++) {
                var request = new Request(getName(), i);
                executorService.execute(request);
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {

        }
    }
}
