package activeobject.ex1.thread;

import activeobject.ex1.activeobject.ActiveObject;

public class DisplayClientThread extends Thread {

    private final ActiveObject activeObject;

    public DisplayClientThread(ActiveObject activeObject, String name) {
        super(name);
        this.activeObject = activeObject;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i++) {
                var str = Thread.currentThread().getName() + " " + i;
                activeObject.displayString(str);

                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
