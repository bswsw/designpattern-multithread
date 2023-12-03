package singlethread.gate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserThread extends Thread {

    private final Gate gate;
    private final String name;
    private final String address;


    @Override
    public void run() {
        System.out.println("Begin: " + name);
        while (true) {
            gate.pass(name, address);
        }
    }
}
