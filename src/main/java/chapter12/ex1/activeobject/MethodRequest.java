package chapter12.ex1.activeobject;

import chapter12.ex1.future.FutureResult;

abstract class MethodRequest<T> {

    protected final Servant servant;
    protected final FutureResult<T> future;

    protected MethodRequest(Servant servant, FutureResult<T> future) {
        this.servant = servant;
        this.future = future;
    }

    public abstract void execute();
}
