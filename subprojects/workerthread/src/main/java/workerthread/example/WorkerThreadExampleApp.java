package workerthread.example;

import java.util.concurrent.Executors;

public class WorkerThreadExampleApp {

    public static void main(String[] args) {
        Channel channel = new Channel(5);

        new ClientThread("Alice", channel).start();
        new ClientThread("Bobby", channel).start();
        new ClientThread("Chris", channel).start();

        new WorkerThread("worker-0", channel).start();
        new WorkerThread("worker-1", channel).start();
        new WorkerThread("worker-2", channel).start();
        new WorkerThread("worker-3", channel).start();
        new WorkerThread("worker-4", channel).start();

        Executors.newFixedThreadPool(5);
    }
}
