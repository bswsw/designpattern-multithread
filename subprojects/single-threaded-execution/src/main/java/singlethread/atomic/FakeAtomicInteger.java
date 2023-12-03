package singlethread.atomic;

public class FakeAtomicInteger {

    private  int i = 0;

    int getAndIncrement() {
        return ++i;
    }
}
