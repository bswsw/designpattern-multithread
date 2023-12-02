package activeobject.ex1.thread;

import activeobject.ex1.activeobject.ActiveObject;

public class MakerClientThread extends Thread {

    private final ActiveObject activeObject;
    private final char fillchar;

    public MakerClientThread(ActiveObject activeObject, String name) {
        super(name);
        this.activeObject = activeObject;
        this.fillchar = name.charAt(0);
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i++) {
                var result = activeObject.makeString(i, fillchar);

                Thread.sleep(10);

                var value = result.getResultValue();
                System.out.println(Thread.currentThread().getName() + ": value = " + value);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
