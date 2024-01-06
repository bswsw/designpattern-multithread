package threadspecificstorage;

public class ClientThread2 extends Thread {

    private final TSLog log;

    public ClientThread2(String name) {
        super(name);

        log = new TSLog(name + "-log.txt");
    }

    @Override
    public void run() {
        System.out.println(getName() + " BEGIN");

        for (int i = 0; i < 10; i++) {
            log.println("i =" + i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }

        log.close();
        System.out.println(getName() + " END");
    }
}
