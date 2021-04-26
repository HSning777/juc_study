package com.atguigu.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class DeadLockDemo {
    public static void main(String[] args) {
        /*Object objectA = new Object();
        Object objectB = new Object();

        new Thread(()->{
            synchronized (objectA){
                System.out.println(Thread.currentThread().getName() + "持有A锁，希望获得B锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (objectB){
                    System.out.println(Thread.currentThread().getName() + "获取到B锁");
                }
            }
        },"A").start();

        new Thread(()->{
            synchronized (objectB){
                System.out.println(Thread.currentThread().getName() + "持有B锁，希望获得A锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (objectA){
                    System.out.println(Thread.currentThread().getName() + "获取到A锁");
                }
            }
        },"B").start();*/
        Lock lockA = new ReentrantLock();
        Lock lockB = new ReentrantLock();
        new Thread(()->{
            lockA.lock();
            try {
                System.out.println(Thread.currentThread().getName()+"持有A锁，希望获得B锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lockB.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + "获取到B锁");
                } finally {
                    lockB.unlock();
                }
            } finally {
                lockA.unlock();
            }
        },"A").start();
        new Thread(()->{
            lockB.lock();
            try {
                System.out.println(Thread.currentThread().getName()+"持有B锁，希望获得A锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lockA.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + "获取到A锁");
                } finally {
                    lockA.unlock();
                }
            } finally {
                lockB.unlock();
            }
        },"B").start();
    }
}
