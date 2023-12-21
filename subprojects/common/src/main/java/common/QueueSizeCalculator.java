package common;

public interface QueueSizeCalculator {

    default int calc(int target, int total) {
        return (target + 1) % total;
    }
}
