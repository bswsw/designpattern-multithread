package activeobject.ex1.activeobject;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SchedulerThread extends Thread {

    private final ActivationQueue queue;

    public void invoke(MethodRequest request) {
        queue.putRequest(request);
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("schedulerThread...");
            var request = queue.takeRequest();
            request.execute();
        }
    }
}
