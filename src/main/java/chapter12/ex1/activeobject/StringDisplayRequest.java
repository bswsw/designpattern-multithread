package chapter12.ex1.activeobject;

public class StringDisplayRequest extends MethodRequest<Object> {

    private final String str;

    public StringDisplayRequest(Servant servant, String str) {
        super(servant, null);
        this.str = str;
    }

    @Override
    public void execute() {
        servant.displayString(str);
    }
}
