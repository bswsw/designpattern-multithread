# Thread Specific Storage 패턴

## ??
쓰레드 고유의 저장고, 각 쓰레드의 기억영역

## 다른 이름
- Per Thread Attribute
- Thread Specific Data
- Thread Specific Field
- Thread Local Storage

## java.lang.ThreadLocal

### set
- 메소드를 호출한 쓰레드에 데이터를 보관한다.
- 현재의 쓰레드를 조사하고 그것을 열쇠처럼 사용하여 인스턴스를 보관한다.

### get
- 메소드를 호출한 쓰레드에서 데이터를 꺼낸다.
- 보관한 데이터가 없다면 null을 리턴한다.


```java
public class ThreadLocalTestApp {
    public static void main(String[] args) {
        var threadLocal = new ThreadLocal<Integer>();

        threadLocal.set(1); // main 쓰레드

        System.out.println("A: " + threadLocal.get()); // 1

        new Thread(() -> System.out.println("B: " + threadLocal.get())).start(); // null
    }
}
```

위 예제에서 A, B는 각각 1과 null이 찍힌다.

set, get 메소드는 항상 Thread.currentThread() 를 호출해서 메소드를 호출한 쓰레드의 ThreadLocal을 가져온다.

set이 메인 쓰레드에서 일어났기 때문에 메인 메소드에서 호출한 get 메소드에서만 1이 리턴되고 다른 쓰레드에서 호출한 get은 기본값인 null을 리턴한다. 

그럼 자식 쓰레드에서 부모 쓰레드의 ThreadLocal 정보를 사용할 수는 없을까?


## InheritableThreadLocal

```java
public class InheritableThreadLocalTestApp {

    public static void main(String[] args) {
        var threadLocal = new InheritableThreadLocal<Integer>(); // 여기만 변경됨.

        threadLocal.set(1);

        System.out.println("[main thread] value: " + threadLocal.get()); // 1

        new Thread(() -> System.out.println("B: " + threadLocal.get())).start(); // 1
    }
}
```

위 코드에서 threadLocal의 선언만 변경되었다.

InheritableThreadLocal은 자식 쓰레드로 ThreadLocal을 상속 시키기 때문에 A, B 모두 get 메소드에서 1을 리턴한다.


```java
// ThreadLocal.java
public class ThreadLocal<T> {
    // ...
    
    T childValue(T parentValue) {
        throw new UnsupportedOperationException();
    }
    
    // ...
    
    ThreadLocalMap getMap(Thread t) {
        return t.threadLocals;
    }
    
    // ...

    void createMap(Thread t, T firstValue) {
        t.threadLocals = new ThreadLocalMap(this, firstValue);
    }
}

// InheritableThreadLocal.java
public class InheritableThreadLocal<T> extends ThreadLocal<T> {
    public InheritableThreadLocal() {}

    protected T childValue(T parentValue) {
        return parentValue;
    }

    ThreadLocalMap getMap(Thread t) {
        return t.inheritableThreadLocals;
    }

    void createMap(Thread t, T firstValue) {
        t.inheritableThreadLocals = new ThreadLocalMap(this, firstValue);
    }
}

// ThreadLocal.java
public class ThreadLocal<T> {
    // ...

    static class ThreadLocalMap {
        private ThreadLocalMap(ThreadLocalMap parentMap) {
            Entry[] parentTable = parentMap.table;
            int len = parentTable.length;
            setThreshold(len);
            table = new Entry[len];

            for (Entry e : parentTable) {
                if (e != null) {
                    @SuppressWarnings("unchecked")
                    ThreadLocal<Object> key = (ThreadLocal<Object>) e.get();
                    if (key != null) {
                        Object value = key.childValue(e.value); // 여기~
                        Entry c = new Entry(key, value);
                        int h = key.threadLocalHashCode & (len - 1);
                        while (table[h] != null)
                            h = nextIndex(h, len);
                        table[h] = c;
                        size++;
                    }
                }
            }
        }       
    }
    
    // ...
}
```

ThreadLocal은 내부적으로 Entry 배열으로 관리되는 ThreadLocalMap을 가지고 있다.

InheritableThreadLocal는 ThreadLocal을 상속하고 있고 오버라이드한 메소드들을 둘이 비교해보면 차이가 있다.

그런데 이 ThreadLocalMap은 어디서 정해지는 걸까?

getMap 메소드를 보면 Thread에서 threadLocalMap을 가져오는걸 볼 수 있다.


```java
public class Thread implements Runnable {
    // ...

    ThreadLocal.ThreadLocalMap threadLocals = null;
    ThreadLocal.ThreadLocalMap inheritableThreadLocals = null;
    
    // ...

    private Thread(ThreadGroup g, Runnable target, String name,
                   long stackSize, AccessControlContext acc,
                   boolean inheritThreadLocals) {
        // ...
        if (inheritThreadLocals && parent.inheritableThreadLocals != null)
            this.inheritableThreadLocals =
                    ThreadLocal.createInheritedMap(parent.inheritableThreadLocals);
        
        // ...
    }
    
    // ...
}
```

쓰레드를 생성할 때 inheritThreadLocals 플래그에 따라 부모의 ThreadLocalMap을 상속받을 수 있다. 


## 비틀어보기

```java
public class ThreadLocalTestApp {
    public static void main(String[] args) {
        var threadLocal = ThreadLocal.withInitial(() -> 0);

        threadLocal.set(1);

        System.out.println("A: " + threadLocal.get()); // 1

        new Thread(() -> System.out.println("B: " + threadLocal.get())).start(); // 0
    }
}
```

ThreadLocal get 메소드의 기본값은 null이다.

그런데 왜 B는 0이 찍혔을까?

```java
public class ThreadLocal<T> {
    
    // ...

    protected T initialValue() {
        return null;
    }
    
    // ...
}
```

기본값은 null이지만 initalValue 메소드를 호출해서 기본값을 리턴한다.

```java
static final class SuppliedThreadLocal<T> extends ThreadLocal<T> {

    private final Supplier<? extends T> supplier;

    SuppliedThreadLocal(Supplier<? extends T> supplier) {
        this.supplier = Objects.requireNonNull(supplier);
    }

    @Override
    protected T initialValue() {
        return supplier.get();
    }
}
```

withInitial 팩토리 메소드를 사용하면 ThreadLocal의 또 다른 상속 클래스인 SuppliedThreadLocal 객체를 생성하고 supplier로 받은 값을 기본값으로 준다.

supplier기 때문에 호출할 때 마다 기본값이 변경되게끔 할 수도 있다.

```java
public class ThreadLocalTestApp {
    public static void main(String[] args) {
        var threadLocal = ThreadLocal.withInitial(() -> new Random().nextInt());

        threadLocal.set(1);

        System.out.println("A: " + threadLocal.get());

        new Thread(() -> System.out.println("B: " + threadLocal.get())).start();

        new Thread(() -> System.out.println("C: " + threadLocal.get())).start();
    }
}
```
