package org.yoqu.javastudy.thread.lock;

/**
 * @author yoqu
 * @date 2017年05月10日
 * @time 下午3:26
 * @email wcjiang2@iflytek.com
 */
public class DeadLockDemo {
    private static String A="A";
    public static String B="B";

    public static void main(String[] args) {
        new DeadLockDemo().deadLock();
    }

    public void deadLock(){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (A){
                    try {
                        Thread.sleep(2000);
                        synchronized (B){
                            System.out.println("t1 B");
                        }
                        System.out.println("t1 A");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (B){
                    System.out.println("t2 B");
                    synchronized (A){
                        System.out.println("t2 A");
                    }
                }
            }
        });
        t1.start();
        t2.start();
    }
}
