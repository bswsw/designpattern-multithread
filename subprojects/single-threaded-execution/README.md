# Introductuon 01 Java 언어의 쓰레드

# Runnable, Thread

Thread 하위 클래스를 만들 때 run 메소드를 재정의하고 실행할 때 start 메소드를 호출한다.

start 메소드를 실행 시키면 스레드 그룹에 스레드를 추가하고 start0 메소드를 실행 시켜서 스레드를 시작한다.

```java
    public synchronized void start() {
        /**
         * This method is not invoked for the main method thread or "system"
         * group threads created/set up by the VM. Any new functionality added
         * to this method in the future may have to also be added to the VM.
         *
         * A zero status value corresponds to state "NEW".
         */
        if (threadStatus != 0)
            throw new IllegalThreadStateException();

        /* Notify the group that this thread is about to be started
         * so that it can be added to the group's list of threads
         * and the group's unstarted count can be decremented. */
        group.add(this);

        boolean started = false;
        try {
            start0();
            started = true;
        } finally {
            try {
                if (!started) {
                    group.threadStartFailed(this);
                }
            } catch (Throwable ignore) {
                /* do nothing. If start0 threw a Throwable then
                  it will be passed up the call stack */
            }
        }
    }
```

https://github.com/openjdk/jdk/blob/master/src/java.base/share/native/libjava/Thread.c

run 메소드는 단순히 어떤 로직이 있을 뿐이다.


### start, run 메소드 호출 결과 비교
```java
public class MyThread extends Thread {

    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println("MyThread current thread : " + Thread.currentThread());
    }
}


var myThread = new MyThread("my-thread");

myThread.run();
myThread.start();
```

```
MyThread current thread : Thread[main,5,main]
MyThread current thread : Thread[my-thread,5,main]
```


run 메소드는 왜 구현(implement)이 아닌 재정의(override)일까?

이미 책의 Runnable 사용법을 보면서 이해 했겠지만 기본 구현을 보면 target, 즉 Runnable의 run을 실행하려는 것을 볼 수 있다.

```java
    @Override
    public void run() {
        if (target != null) {
            target.run();
        }
    }

```

그렇기 때문에 Thread를 상속했다면 run을 재정의하거나 생성자에 Runnable을 끼워넣어줘야 한다.


그러면 Thread 객체가 안전하길 원한다면 오해를 살만한 run 메소드는 클라이언트 코드에서 직접 호출하지 못하도록 protected 등으로 막지 않았을까?

Thread는 Runnable의 구현체인데 Runnable은 run 메소드를 하나 가지고 있는 단순한 인터페이스 이다.

Runnable은 매우 다양한 곳에서 사용중인데, 

Thread 처럼 실행을 위한 기능들인 CompletableFuture, Executor, Task 등에서도 많이 쓰이지만, Optional 같은데서도 쓰인다.

```java
    public void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) {
        if (value != null) {
            action.accept(value);
        } else {
            emptyAction.run();
        }
    }
```


# Synchronized

```java
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
```

위 클래스가 있을 때 아래 로직은 어떤 순서로 실행 될까?

```java
public class SynchronizeSampleApp {

    public static void main(String[] args) {
        var myBank = new Bank(1000, "mybank");

        new Thread(() -> myBank.deposit(1000)).start(); // 10s sleep
        new Thread(() -> myBank.withdraw(1000)).start();
        new Thread(() -> System.out.println("bank name is " + myBank.getName())).start();

        new Thread(Bank::classLock1).start(); // 5s sleep
        new Thread(myBank::classLock2).start();
    }
}
```

```
# 아래 3개 순서는 랜덤일 수 있음
class lock 1: javathread.synchronize.Bank
bank name is mybank
deposit: 1000

# 5초 뒤
class lock 2: javathread.synchronize.Bank

# 5초 뒤
withdraw: 1000
```

클래스 락과 인스턴스 락은 별개로 동작한다는 것을 알 수 있다.


# wait, notify, notifyAll

wait 셋에 들어간 스레드는 notify(All) 이 호출되면 깨어나면서 다음 라인으로 진행을 한다.

notify, notifyAll은 같은 인스턴스의 wait 셋에 대해서만 작용한다.

```java
var waitNotify3 = new WaitNotify();
new Thread(() -> waitNotify3.methodWait("name3")).start();

var waitNotify4 = new WaitNotify();
new Thread(() -> waitNotify4.methodWait("name4")).start();

Thread.sleep(1000);

new Thread(() -> waitNotify3.methodNotifyAll()).start();
```

```
[WaitNotify] wait: name3
[WaitNotify] wait: name4
[WaitNotify] notifyAll
[WaitNotify] wakeup: name3
```

```java
var onlyWait2 = new OnlyWait();
new Thread(() -> onlyWait2.methodWait("name1")).start();

var waitNotify2 = new WaitNotify();
new Thread(() -> waitNotify2.methodWait("name1")).start();

Thread.sleep(1000);

new Thread(() -> waitNotify2.methodNotifyAll()).start();
```

```
[WaitNotify] wait: name1
[OnlyWait] wait: name1
[WaitNotify] notifyAll
[WaitNotify] wakeup: name1
```

