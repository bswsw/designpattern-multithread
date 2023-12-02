package activeobject.ex1.activeobject;

import activeobject.ex1.future.RealResult;
import activeobject.ex1.future.Result;

class Servant implements ActiveObject {

    @Override
    public Result<String> makeString(int count, char fillchar) {
        var buffer = new char[count];

        for (int i = 0; i < count; i++) {
            buffer[i] = fillchar;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new RealResult<>(new String(buffer));
    }

    @Override
    public void displayString(String string) {
        try {
            System.out.println("displayString: " + string);
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();//
        }
    }
}
