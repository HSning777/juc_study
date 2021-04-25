package com.atguigu.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tickets {
    private int num = 50;
    private Lock lock = new ReentrantLock();

    public void sale(){
        lock.lock();
        try {
            if(num > 0){
                System.out.println(Thread.currentThread().getName() + "卖出了第" + num-- + "张票，还剩：" + num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
