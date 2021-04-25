package com.atguigu.juc;

import java.util.ConcurrentModificationException;

//arraylist 扩容到原值一半  hashmap扩容到原值一倍
//一次sql会话，要么所有sql全成功，要么全失败
//父类的引用指向子类实例就叫多态
public class ProdConsumerDemo {
    public static void main(String[] args) {
        sub sub = new sub();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    sub.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    sub.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

    }
}

class sub{
    private int num = 0;

    public synchronized void increment() throws InterruptedException {
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
    }
}



