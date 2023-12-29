package future.example;

public class Host {

    public Data request(final int count, final char c) {
        System.out.println("  request(" + count + ", " + c + ") BEGIN");

        final var future = new FutureData();

        new Thread(() -> {
            var real = new RealData(count, c);
            future.setRealData(real);
        }).start();

        System.out.println("  request(" + count + ", " + c + ") END");

        return future;
    }
}
