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
        Thread t1= new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (A){
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程1睡眠完毕");
                    synchronized (B){//3. 由于睡眠过后B值一直被线程2征用.且线程2在等待线程1的A锁释放导致 两个线程发生死锁问题
                        System.out.println("thread1");
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (B){//1.进入B
                    System.out.println("线程2锁定B值");
                    synchronized (A){//2.由于线程1还在sleep中,没有释放A线程所以无法进入方法体中
                        System.out.println("thread2");
                    }
                }
            }
        });
        t1.start();
        t2.start();
    }
}
