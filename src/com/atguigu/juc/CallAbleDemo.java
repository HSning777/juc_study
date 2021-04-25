package com.atguigu.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class MyThread implements Callable<String>{

    @Override
    public String call() throws Exception {
        System.out.println("-----come in");
        Thread.sleep(5000);
        return "hello java1116";
    }
}
/*
* callable接口实现多线程可以有返回值
* */
public class CallAbleDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new MyThread());
        Thread thread = new Thread(futureTask);
        thread.start();

        System.out.println("main线程结束");
        System.out.println(futureTask.get());//要放在最后一行
    }
}
