package singlethread.synchronize;

public class Bank {

    private int money;
    private final String name;

    static synchronized void classLock1() {
        System.out.println("class lock 1: " + Bank.class.getName());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    void classLock2() {
        synchronized (Bank.class) {
            System.out.println("class lock 2: " + this.getClass().getName());
        }
    }

    public Bank(int money, String name) {
        this.money = money;
        this.name = name;
    }

    synchronized void deposit(int money) {
        System.out.println("deposit: " + money);
        this.money += money;

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized boolean withdraw(int money) {
        System.out.println("withdraw:" + money);

        if (this.money >= money) {
            this.money -= money;
            return true;
        } else {
            return false;
        }
    }

    String getName() {
        return this.name;
    }

}
