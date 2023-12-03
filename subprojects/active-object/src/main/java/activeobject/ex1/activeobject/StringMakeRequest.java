package activeobject.ex1.activeobject;

import activeobject.ex1.future.FutureResult;

public class StringMakeRequest extends MethodRequest<String> {

    private final int count;
    private final char fillchar;

    public StringMakeRequest(Servant servant, FutureResult<String> future, int count, char fillchar) {
        super(servant, future);
        this.count = count;
        this.fillchar = fillchar;
    }

    @Override
    public void execute() {
        var result = servant.makeString(count, fillchar);
        future.setResult(result);
    }
}
