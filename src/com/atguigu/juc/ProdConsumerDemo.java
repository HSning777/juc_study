package com.atguigu.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//arraylist 扩容到原值一半  hashmap扩容到原值一倍
//一次sql会话，要么所有sql全成功，要么全失败
//父类的引用指向子类实例就叫多态

/*
* juc口诀：
* 高内聚低耦合的前提下，线程操作资源类
* 判断，干活，通知
* 为了防止虚假唤醒，判断使用while
* */
public class ProdConsumerDemo {
    public static void main(String[] args) {
        sub sub = new sub();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                sub.increment();
            }
        },"A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                sub.decrement();
            }
        },"B").start();

    }
}

class sub{
    private int num = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    /*public synchronized void increment() throws InterruptedException {
        if(this.num != 0){
            this.wait();
        }

        num++;
        System.out.println(Thread.currentThread().getName() + ":" + this.num);

        this.notify();
    }

    public synchronized void decrement() throws InterruptedException {
        if(this.num == 0){
            this.wait();
        }

        num--;
        System.out.println(Thread.currentThread().getName() + ":" + this.num);

        this.notify();
    }*/

    public void increment() {
        lock.lock();
        try {
            while(num != 0){
                condition.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName() + ":" + num);
            condition.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public void decrement(){
        lock.lock();
        try {
            while(num == 0){
                condition.await();
            }
            num--;
            System.out.println(Thread.currentThread().getName() + ":" + num);
            condition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}



