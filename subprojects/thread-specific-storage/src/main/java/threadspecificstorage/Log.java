package threadspecificstorage;

public class Log {
    private static final ThreadLocal<TSLog> tsLogCollector = new ThreadLocal<>();

    public static void println(String s) {
        getTSLog().println(s);
    }

    public static void close() {
        getTSLog().close();
    }

    private static TSLog getTSLog() {
        var tsLog = tsLogCollector.get();

        if (tsLog == null) {
            tsLog = new TSLog(Thread.currentThread().getName() + "-log.txt");
            tsLogCollector.set(tsLog);
            startWatcher(tsLog);
        }

        return tsLog;
    }

    private static void startWatcher(TSLog tsLog) {
        var t = Thread.currentThread();

        var watcher = new Thread(() -> {
            System.out.println("watcher for " + t.getName() + " BEGIN");

            try {
                t.join();
            } catch (InterruptedException e) {
            }

            tsLog.close();
            System.out.println("watcher for " + t.getName() + " END");
        });

        watcher.start();
    }
}
