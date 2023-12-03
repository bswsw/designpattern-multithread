package activeobject.ex1.activeobject;

public class ActiveObjectFactory {

    public static ActiveObject createActiveObject() {
        var servant = new Servant();
        var queue = new ActivationQueue();
        var scheduler = new SchedulerThread(queue);
        var proxy = new Proxy(scheduler, servant);
        scheduler.start();
        return proxy;
    }
}
