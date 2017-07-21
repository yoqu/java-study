package org.yoqu.javastudy.blockqueue;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yoqu
 * @date 2017年07月20日
 * @time 上午9:06
 * @email wcjiang2@iflytek.com
 */
public class BlockingQueue {
    private final int minSize = 0;
    private final int maxSize;
    private LinkedList<Object> list = new LinkedList<>();
    private AtomicInteger size = new AtomicInteger(0);

    public Object lock = new Object();

    public BlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void put(Object obj){
        synchronized (lock){
            if(size.get()==maxSize){
                try {
                    lock.wait();
                } catch (InterruptedException e) {

                }
            }
            list.add(obj);
            System.out.println("add value:"+obj);
            size.incrementAndGet();
            lock.notify();
        }
    }

    public Object take(){
        Object obj = null;
        synchronized (lock){
            if(size.get()==minSize){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            obj = list.removeFirst();
            System.out.println("remove value: "+obj);
            size.decrementAndGet();
            lock.notify();
        }
        return obj;
    }

    public static void main(String[] args) {
        BlockingQueue blockingQueue = new BlockingQueue(5);
        blockingQueue.put("1");
        blockingQueue.put("2");
        blockingQueue.put("3");
        blockingQueue.put("4");
        Thread t1 = new Thread(){
            @Override
            public void run() {
                blockingQueue.put("5");
                blockingQueue.put("6");
                blockingQueue.put("7");
                blockingQueue.take();
            }
        };
        Thread t2= new Thread(){
            @Override
            public void run() {
                blockingQueue.take();
                blockingQueue.put("8");
                blockingQueue.put("9");
            }
        };

        t1.start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2.start();
        blockingQueue.take();
        blockingQueue.take();
    }
}
