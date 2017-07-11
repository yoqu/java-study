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
    private int casResult =0;

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
