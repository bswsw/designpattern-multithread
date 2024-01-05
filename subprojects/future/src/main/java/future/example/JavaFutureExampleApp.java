package future.example;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * @author alan.bae
 * @since 2023/12/29
 */
public class JavaFutureExampleApp {

    public static void main(String[] args) throws InterruptedException {
        var es = Executors.newCachedThreadPool();

        var callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(3000);
                return "sadsadadsda";
            }
        };

        System.out.println("future start...");

        var f = es.submit(callable);

        System.out.println("future ing... " + f.isDone());

        Thread.sleep(1000);
        System.out.println("future ing... " + f.isDone());

        Thread.sleep(1000);
        System.out.println("future ing... " + f.isDone());

        Thread.sleep(1000);
        System.out.println("future ing... " + f.isDone());

        try {
            var data = f.get();
            System.out.println(data);
        } catch (InterruptedException e) {

        } catch (ExecutionException e) {

        }

        System.out.println("future completed " + f.isDone());

        es.shutdown();
    }
}
