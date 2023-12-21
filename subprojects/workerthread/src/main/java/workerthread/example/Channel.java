package workerthread.example;

import common.QueueSizeCalculator;

public class Channel implements QueueSizeCalculator {

    private final Request[] requestQueue;

    private int tail;
    private int head;
    private int count;

    public Channel(int maxRequest) {
        this.requestQueue = new Request[maxRequest];
        this.tail = 0;
        this.head = 0;
        this.count = 0;
    }

    public synchronized void putRequest(Request request) {
        while (count >= requestQueue.length) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }

        requestQueue[tail] = request;
        tail = (tail + 1) % requestQueue.length;
        count++;
        notifyAll();
    }

    public synchronized Request takeRequest() {
        while (count <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }

        var request = requestQueue[head];
        head = (head + 1) % requestQueue.length;
        count--;
        notifyAll();

        return request;
    }

}
