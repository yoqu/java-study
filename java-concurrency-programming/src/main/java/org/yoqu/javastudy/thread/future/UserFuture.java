package org.yoqu.javastudy.thread.future;

import java.util.concurrent.*;

/**
 * @author yoqu
 * @date 2017年08月01日
 * @time 下午4:57
 * @email wcjiang2@iflytek.com
 */
public class UserFuture implements Callable<String> {
    String name;

    public UserFuture(String name) {
        this.name = name;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        FutureTask<String> f1 = new FutureTask<String>(new UserFuture("v1"));
        FutureTask<String> f2 = new FutureTask<String>(new UserFuture("v2"));
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(f1);
        executorService.submit(f2);
        System.out.println("执行其他任务中..");
        Thread.sleep(3000);
        System.out.println(f1.get());
        System.out.println(f2.get());
        System.out.println("打印结果");
    }

    @Override
    public String call() throws Exception {
        System.out.println("开始调用");
        Thread.sleep(5000);
        String result = name + "处理完成";
        return result;
    }
}
