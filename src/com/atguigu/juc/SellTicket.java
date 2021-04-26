package com.atguigu.juc;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SellTicket {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
//        new Thread(() -> { for (int i = 0; i <= 31; i++) ticket.sale(); },"A").start();
//        new Thread(() -> { for (int i = 0; i <= 31; i++) ticket.sale(); },"B").start();
//        new Thread(() -> { for (int i = 0; i <= 31; i++) ticket.sale(); },"C").start();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5, 1L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(3), new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 3; i++) {
            Future<?> submit = threadPoolExecutor.submit(() -> {
                for (int j = 0; j < 31; j++) {
                    ticket.sale();
                }
            });
        }
        threadPoolExecutor.shutdown();
    }

}

class Ticket{
    private int num = 30;
    private Lock lock = new ReentrantLock(true);

    void sale(){
        lock.lock();
        try {
            if(num > 0){
                System.out.println(Thread.currentThread().getName()+"卖出第：" + num-- + "张票，还剩下：" + num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}