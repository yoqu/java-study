package org.yoqu.javastudy.waitnotify;

import java.util.concurrent.CountDownLatch;

/**
 * @author yoqu
 * @date 2017年07月20日
 * @time 上午8:58
 * @email wcjiang2@iflytek.com
 */
public class NotifyTest {
    public static String lock = "lock";

    public static void main(String[] args) {
        NotifyTest notifyTest = new NotifyTest();
//        notifyTest.run();
        notifyTest.runCountDownLatch();
    }

    public void runCountDownLatch(){
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread3 thread3 = new Thread3(countDownLatch);
        Thread4 thread4 = new Thread4(countDownLatch);
        thread4.start();
        thread3.start();

    }

    public void run() {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread2.start();
        thread1.start();
    }

    class Thread3 extends Thread {
        CountDownLatch countDownLatch;
        public Thread3(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
//            synchronized (lock){
            for (int i = 0; i < 100; i++) {
                System.out.println(i);
                if (i == 20) {
                    countDownLatch.countDown();
                    System.out.println("countDownLatch 减1");
                }
            }
//            }
        }
    }

    class Thread4 extends Thread {
        CountDownLatch countDownLatch;

        public Thread4(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
//            synchronized (lock){
            try {
                countDownLatch.await();
                System.out.println("来了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            }
        }
    }

    class Thread1 extends Thread {
        @Override
        public void run() {
            synchronized (lock) {
                for (int i = 0; i < 200; i++) {
                    System.out.println(i);
                    if (i == 30) {
                        System.out.println("开始发送通知");
                        lock.notify();
                    }
                }
            }
        }
    }

    class Thread2 extends Thread {
        @Override
        public void run() {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("成功收到通知");
            }
        }
    }
}
