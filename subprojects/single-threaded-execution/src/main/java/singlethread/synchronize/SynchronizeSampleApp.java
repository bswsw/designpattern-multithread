package singlethread.synchronize;

public class SynchronizeSampleApp {

    public static void main(String[] args) {
        var myBank = new Bank(1000, "mybank");

        new Thread(() -> myBank.deposit(1000)).start();
        new Thread(() -> myBank.withdraw(1000)).start();
        new Thread(() -> System.out.println("bank name is " + myBank.getName())).start();

        new Thread(Bank::classLock1).start();
        new Thread(myBank::classLock2).start();
    }
}
