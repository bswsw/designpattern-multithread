package singlethread.gate;

public class GateApp {

    public static void main(String[] args) {
        var gate = new Gate();

        new UserThread(gate, "alice", "alaska").start();
        new UserThread(gate, "bobby", "brazil").start();
        new UserThread(gate, "chris", "canada").start();

    }
}
