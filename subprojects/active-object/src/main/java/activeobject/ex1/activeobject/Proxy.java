package activeobject.ex1.activeobject;

import activeobject.ex1.future.FutureResult;
import activeobject.ex1.future.Result;

class Proxy implements ActiveObject {

    private final SchedulerThread scheduler;
    private final Servant servant;

    public Proxy(SchedulerThread scheduler, Servant servant) {
        this.scheduler = scheduler;
        this.servant = servant;
    }

    @Override
    public Result<String> makeString(int count, char fillchar) {
        var future = new FutureResult<String>();
        scheduler.invoke(new StringMakeRequest(servant, future, count, fillchar));
        return future;
    }

    @Override
    public void displayString(String string) {
        scheduler.invoke(new StringDisplayRequest(servant, string));
    }
}
