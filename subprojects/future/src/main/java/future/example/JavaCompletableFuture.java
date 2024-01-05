package future.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author alan.bae
 * @since 2023/12/29
 */
public class JavaCompletableFuture {
    public static void main(String[] args) throws InterruptedException {
        var f = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "asdsadsad";
        });

        System.out.println("future ing... " + f.isDone());

        Thread.sleep(1000);
        System.out.println("future ing... " + f.isDone());

        Thread.sleep(1000);
        System.out.println("future ing... " + f.isDone());

        Thread.sleep(1000);
        System.out.println("future ing... " + f.isDone());

        var ff = f
                .thenApply(a -> a + " thenApply")
                .thenApply(b -> b + " sadsasda")
                .thenApply(b -> b + " sadsasda")
                .thenApply(b -> b + " sadsasda")
                .thenApply(b -> b + " sadsasda")
                .thenApply(b -> b + " sadsasda")
                .thenApply(b -> b + " sadsasda")
                .thenApply(b -> b + " sadsasda");

        try {
            var data = ff.get();
            System.out.println(data);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        System.out.println("future completed " + f.isDone());
    }
}
