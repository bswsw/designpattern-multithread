package activeobject.ex1.activeobject;

import activeobject.ex1.future.Result;

public interface ActiveObject {
    Result<String> makeString(int count, char fillchar);

    void displayString(String string);
}
