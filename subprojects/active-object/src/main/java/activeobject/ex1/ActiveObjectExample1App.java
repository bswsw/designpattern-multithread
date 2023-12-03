package activeobject.ex1;

import activeobject.ex1.activeobject.ActiveObjectFactory;
import activeobject.ex1.thread.DisplayClientThread;
import activeobject.ex1.thread.MakerClientThread;

public class ActiveObjectExample1App {
    public static void main(String[] args) {
        var activeObject = ActiveObjectFactory.createActiveObject();

        new MakerClientThread(activeObject, "Alice").start();
        new MakerClientThread(activeObject, "bobby").start();

        new DisplayClientThread(activeObject, "Chris").start();
    }
}
