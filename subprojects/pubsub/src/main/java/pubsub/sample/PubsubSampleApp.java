package pubsub.sample;

public class PubsubSampleApp {

    public static void main(String[] args) {
        var m = new Market();

        new Producer(m).start();
        new Consumer(m).start();
    }
}
