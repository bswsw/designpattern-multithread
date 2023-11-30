package chapter12.ex1;

import chapter12.ex1.activeobject.ActiveObjectFactory;
import chapter12.ex1.thread.DisplayClientThread;
import chapter12.ex1.thread.MakerClientThread;

public class Main {

    public static void main(String[] args) {
        var activeObject = ActiveObjectFactory.createActiveObject();

        new MakerClientThread(activeObject, "Alice").start();
        new MakerClientThread(activeObject, "bobby").start();

        new DisplayClientThread(activeObject, "Chris").start();
    }
}
