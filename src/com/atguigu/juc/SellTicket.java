package com.atguigu.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SellTicket {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
//        new Thread(() -> { for (int i = 0; i <= 31; i++) ticket.sale(); },"A").start();
//        new Thread(() -> { for (int i = 0; i <= 31; i++) ticket.sale(); },"B").start();
//        new Thread(() -> { for (int i = 0; i <= 31; i++) ticket.sale(); },"C").start();
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