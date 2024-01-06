package threadspecificstorage.notuse;

import threadspecificstorage.notuse.Log;

public class NotUseThreadSpecificStorageSampleApp {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("BEGIN");

        for (int i = 0; i < 10; i++) {
            Log.println("main: i =" + i);

            Thread.sleep(100);
        }

        Log.close();
        System.out.println("END");
    }
}
