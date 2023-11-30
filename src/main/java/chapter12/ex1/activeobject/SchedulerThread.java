package chapter12.ex1.activeobject;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SchedulerThread extends Thread {

    private final ActivationQueue queue;

    public void invoke(MethodRequest request) {
        queue.putRequest(request);
    }

    @Override
    public void run() {
        var request = queue.takeRequest();
        request.execute();
    }
}
