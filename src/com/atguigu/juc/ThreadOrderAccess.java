package com.atguigu.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareResource{
    int flag = 1;
    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void print5(){
        lock.lock();
        try{
            while(flag!=1){
                condition1.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + ": AA");
            }
            flag = 2;
            condition2.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void print10(){
        lock.lock();
        try {
            while(flag != 2){
                condition2.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + ": BB");
            }
            flag = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print15(){
        lock.lock();
        try {
            while(flag != 3){
                condition3.await();
            }

            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + ": CC");
            }
            flag = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class ThreadOrderAccess {
    public static void main(String[] args) {
        ShareResource resource = new ShareResource();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                resource.print5();
            }
        },"t1").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                resource.print10();
            }
        },"t2").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                resource.print15();
            }
        },"t3").start();
    }
}
