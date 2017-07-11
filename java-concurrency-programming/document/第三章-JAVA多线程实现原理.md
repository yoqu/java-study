# 多线程实现原理


## synchornized 实现原理

再JAVA1.6对synchronized进行优化后，能够减少获得锁和释放锁带来的性能消耗，主要是引入了偏向锁和轻量级锁。还有优化了锁的存储结构和升级结构。

再jvm规范中，看到synchornized实现原理是基于进入退出Monitor对象实现方法同步和代码同步。代码块同步使用monitorenter和monitorexit指令实现，方法同步另外一种方式实现。

* `monitorentor`指令是编译后查询到同步代码块的开始位置
* `monitorexit`指令是插入方法结束和异常处。

### JAVA对象头

synchronized用的锁是存在java对象头中的。JAVA对象头中的mark word 存储对象的`HashCode`、`粉黛年龄`和`锁标记位`。

> 原理：根据JAVA Object Model定义，Object Header是一个2字(1 word =4 byte)长度的存储区域。
>
> 第一个长度区域用于标记同步，GC以及hash code，成为mark word。第二字长度区域指向对象的class
>
> mark word是轻量级实现的关键



### 轻量级锁

#### 	轻量级锁加锁

​	线程再执行同步块代码之前，jvm会为当前线程分配lock record，并复制对象头中的mark word到lock record 中，然后使用CAS将mark word替换为指向lock record的指针。如果成功就获得锁执行同步体，如果失败，表示其他线程使用中，调用OS的互斥原语，挂起当前线程，直到被唤醒。

#### 轻量级解锁


​	轻量级解锁时，会使用原子的CAS将lock record替换回对象头中，成功表示没有竞争，如果失败表示有锁竞争，锁会被释放并唤醒被挂起的线程。

![JAVA轻量级加锁流程图.png](http://upload-images.jianshu.io/upload_images/1489067-5139101232327389.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 锁的优缺点对比

|  锁   |         优点          |           缺点           |       适用场景        |
| :--: | :-----------------: | :--------------------: | :---------------: |
| 偏向锁  |    加锁和解锁不需要额外消耗     | 如果线程存在锁竞争，会带来额外锁撤销的消耗  | 适用于只有一个线程访问同步块场景  |
| 轻量级锁 | 竞争的锁不会阻塞，提高了程序的响应速度 | 如果是中得不到锁竞争的线程，会自旋消耗cpu | 追求响应时间，同步块执行速度非常快 |
| 重量级锁 |  线程竞争不使用自旋，不会消耗CPU  |       线程租塞，响应时间慢       |  追求吞吐量，同步块执行速度较长  |



## 原子操作的实现原理

### 处理器如何实现原子操作

32位IA-32处理器使用基于对`缓存加锁`或`总线加锁`的方式来实现`多`处理器的原子操作。

 当一个处理器读取一个字节时，其他处理器不能访问这个字节的内存地址。

#### 1. 使用总线锁保证原子性

**通过总线锁来保证原子性**，如果多个处理器同时对共享变量进行读改写操作，那么共享变量会被多个处理器同时操作，这样读写操作就不是原子的，操作完后的共享变量值会和期望的不一样。

处理器使用总线锁就是解决这个问题的，总线锁就是使用处理器提供的lock#信号，其他处理器会被阻塞，那么该处理器能够独享内存

#### 2.使用缓存锁保证原子性

在一个时刻，我们只需要保证某个内存地址的操作是原子性即可，但是总线锁定会把CPU和内存之间的通信锁住，使得锁定期间，其他处理器不能操作其他内存地址的数据，所以总线锁定开销较大，在某些场合下使用缓存锁定来代替总线锁定进行优化。

缓存锁定是指内存区域如果被缓存再处理器的缓存行中，且再lock操作期间被锁定，那么当写回内存时，不在总线上声言LOCK#信号，而是修改内部的内存地址，允许缓存一致性机制来保证操作的原子性。

>  有两种情况处理器不会使用缓存锁

- 当操作的数据不能缓存到处理器内部，或操作的数据横跨多个cache line时，处理器会调用总线锁定
- 某些处理器不支持缓存锁定，就算锁定内存区域再处理缓存行中也会调用总线锁定

### java如何实现原子操作

JAVA可以通过锁和循环CAS来实现原子操作

#### 1.使用循环CAS实现原子操作

JVM中的CAS操作正是利用处理器提供的`CMPXCHG`指令实现的，以下代码实现一个基于CAS线程安全的计数器方法saefCount和一个非线程安全的计数器count

```java
package org.yoqu.javastudy.thread.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yoqu
 * @date 2017年06月01日
 * @time 下午4:25
 * @email wcjiang2@iflytek.com
 */
public class CasSafaThread {
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private int result = 0;

    public static void main(String[] args) {
        final CasSafaThread cas = new CasSafaThread();
        Long start = System.currentTimeMillis();
        List<Thread> ts = new ArrayList<>(600);
        for (int i = 0; i < 100; i++) {
            Thread t =new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        cas.count();
                        cas.safeCount();
                    }
                }
            });
            ts.add(t);
        }
        for (Thread t:ts) {
            t.start();
        }
        for (Thread i : ts){
            try{
                i.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("result:"+cas.result);
        System.out.println("atomic:"+cas.atomicInteger.get());
        System.out.println(System.currentTimeMillis()-start);
    }
    private void safeCount(){
        for (;;){
            int i=atomicInteger.get();
            boolean suc = atomicInteger.compareAndSet(i,++i);
            if (suc){
                break;
            }
        }
    }
    private void count(){
        result++;
    }
}
```

从JAVA1.5开始，jdk的并发包就提供了一些类支持原子操作，AtomicBoolean AtomicInteger AtomicLong 

#### 2. cas实现原子操作的三大问题

1. **ABA问题**，因为cas需要再操作值的时候检测值有没有变化，如果没有变化则更新，如果一个值原来是A，又变成了B，又变成了A，那么CAS检查的时候会发现它值没有发生变化，但是实际上却变了，ABA问题的解决思路是使用版本号，变量钱追加版本号，每次更新时候版本+1，jdk 的Atomic包提供了AtomicStampedReference来解决ABA问题，
2. 循环时间长，开销大。如果长时间不成功，会给cpu带来非常大的执行开销，如果JVM能支持处理器提供的pause指令，那么效率会有一定的提升。pause 指令有两个作用：
   1. 延迟流水线执行指令，使得cpu不会消耗过多的执行资源，延迟时间取决具体实现的版本，
   2. 避免退出循环时候因内存顺序冲突引起cpu流水线被清空，提高cpu的执行效率
3. 只能保证一个共享变量的原子操作。当对一个共享变量操作时，我们可以使用循环CAS的方式来保证原子操作，但是对于多个共享变量操作时， 循环CAS无法保证操作的原子性，这个时候可以用锁。或者把多个共享变量合并成一个共享变量来操作。

#### 3. 使用锁机制实现原子操作
锁机制保证了只有获得锁的线程才能够操作锁定的内存区域，jvm内部实现了多种，偏向锁，轻量级锁和互斥锁。除了偏向锁，jvm实现锁的方式都用了循环CAS。