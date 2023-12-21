
WorkerThread 가 왜 Channel에서 관리가 되어야하는지는 잘 모르겠음.

Channel은 지키고 싶은 count만 잘 지키면 될 것 같고 

ClientThread, WorkerThread 각각 알아서 Channel을 주입받아서 외부에서 필요할 때 필요한 만큼 호출하는건 어땠을까?

```java
public class WorkerThreadExampleApp {

    public static void main(String[] args) {
        Channel channel = new Channel(5);

        new ClientThread("Alice", channel).start();
        new ClientThread("Bobby", channel).start();
        new ClientThread("Chris", channel).start();

        new WorkerThread("worker-0", channel).start();
        new WorkerThread("worker-1", channel).start();
        new WorkerThread("worker-2", channel).start();
        new WorkerThread("worker-3", channel).start();
        new WorkerThread("worker-4", channel).start();
    }
}

```

## Channel + WorkerThread vs ExecutorService

부록 2에 나온 ExecutorService를 사용하면 Channel과 WorkerThread를 구현할 필요가 없어 훨씬 간결해진다.

ThreadPoolExecutor를 사용한다.

```JAVA
public void execute(Runnable command) {
    if (command == null)
        throw new NullPointerException();
    
    // 현재 풀상태
    int c = ctl.get();
    
    // 1. 스레드가 corePoolSize 보다 적게 실행되고 있다면 command를 새 스레드로 시작한다.
    // addWorker atomic하다. (ReentrantLock)
    if (workerCountOf(c) < corePoolSize) {
        if (addWorker(command, true))
            return;
        
        // 실패하면 다시 현재 풀 상태 가져온다.
        c = ctl.get();
    }
    
    // 2. 풀이 running 중이면 큐에 넣는다.
    if (isRunning(c) && workQueue.offer(command)) {
        // 다시 상태 체크한다.
        int recheck = ctl.get();
        
        // 2-1. 풀이 running이 아니면 커맨드를 큐에서 지우고 풀을 TERMINATED 처리하고 command를 거절처리한다.
        if (!isRunning(recheck) && remove(command))
            reject(command);
        
        // 2-2. 스레드가 없다면 새로 추가한다.   
        else if (workerCountOf(recheck) == 0) 
            addWorker(null, false);
        
    // 3. 작업을 대기열에 추가할 수 없다면 command를 거절한다. (
    } else if (!addWorker(command, false))
        reject(command);
}
```