package pubsub.sample;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Producer extends Thread {

    private final Market m;

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                m.add();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
