package twophasetermination;

/**
 * @author alan.bae
 * @since 1/5/24
 */
public class ShutdownHookSampleApp {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("application start...");

        var t1 = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
            System.out.println("t1 hook...");
        });

        var t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            System.out.println("t2 hook...");
        });

        var t3 = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
            System.out.println("t3 hook...");
        });

        Runtime.getRuntime().addShutdownHook(t1);
        Runtime.getRuntime().addShutdownHook(t2);
        Runtime.getRuntime().addShutdownHook(t3);

        Thread.sleep(30000);

        System.out.println("application stop...");
    }
}
