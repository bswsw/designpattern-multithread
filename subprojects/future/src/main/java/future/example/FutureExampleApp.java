package future.example;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FutureExampleApp {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main BEGIN");

        var host = new Host();
        var data1 = host.request(10, 'A');
        var data2 = host.request(20, 'B');
        var data3 = host.request(30, 'C');

        System.out.println("Main otherJob BEGIN");
        Thread.sleep(2000);
        System.out.println("Main otherJob END");

        System.out.println("data1 = " + data1.getContent());
        System.out.println("data2 = " + data2.getContent());
        System.out.println("data3 = " + data3.getContent());
        System.out.println("Main END");
    }

    public String getStr(Future<String> fu) {
        try {
            return fu.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
