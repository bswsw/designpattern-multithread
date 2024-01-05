자바에서 sigterm 시그널을 어떻게 받아서 처리할까?

```java
public class ShutdownHookSampleApp {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // logic...
            // 예제의 doShutdown 역할
            // 여러 개 붙일 수 있음.
        }));
    }
}
```

sigterm 시그널이 오면 훅으로 지정된 스레드들을 동시적으로 start하고 join하여 대기한다.
```java
// ApplicationShutdownHooks.java
static void runHooks() {
    Collection<Thread> threads;
    synchronized(ApplicationShutdownHooks.class) {
        threads = hooks.keySet();
        hooks = nul
    }
    
    for (Thread hook : threads) {
        hook.start();
    }
    for (Thread hook : threads) {
        while (true) {
            try {
                hook.join();
                break;
            } catch (InterruptedException ignored) {
            }
        }
    }
}
```

간단 예제
```java
public class ShutdownHookSampleApp {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("application start...");

        var t1 = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
            System.out.println("t1 hook...");
        });

        var t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            System.out.println("t2 hook...");
        });

        var t3 = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
            System.out.println("t3 hook...");
        });

        Runtime.getRuntime().addShutdownHook(t1);
        Runtime.getRuntime().addShutdownHook(t2);
        Runtime.getRuntime().addShutdownHook(t3);

        Thread.sleep(30000);

        System.out.println("application stop...");
    }
}
```


k8s에서 pod를 종료하는 방법

https://kubernetes.io/ko/docs/concepts/workloads/pods/pod-lifecycle/

1. pod 상태가 TERMINATING 상태로 변경되며 트래픽이 차단된다.
2. container들에게 sigterm 시그널을 보낸다.
3. 기본적으로 30초의 시간을 기다리면서 종료를 기다린다. (TerminationGracePeriodSeconds 옵션으로 조절할 수 있음.)
4. 이 시간 내에 정상적으로 종료되지 못한다면 sigkill 시그널을 보낸다.
5. 종료 순서가 중요하거나 sigterm을 못받았을 때를 대비해서 preStop 훅을 구현할 수 있다.

우리는 TerminationGracePeriodSeconds 옵션이 90초 이다.

간단하지만 preStop 훅도 적용되어 있다.
