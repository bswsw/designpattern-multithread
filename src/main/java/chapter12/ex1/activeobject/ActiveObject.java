package chapter12.ex1.activeobject;

import chapter12.ex1.future.Result;

public interface ActiveObject {
    Result<String> makeString(int count, char fillchar);

    void displayString(String string);
}
