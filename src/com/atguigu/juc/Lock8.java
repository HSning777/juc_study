package com.atguigu.juc;

import java.util.concurrent.TimeUnit;

class Phone{
    public static synchronized void email(){
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-----Email");
    }

    public synchronized void SMS(){
        System.out.println("-----SMS");
    }

    public void sayHello(){
        System.out.println("-----hello");
    }
}

public class Lock8 {
    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();
        Phone phone2 = new Phone();


        new Thread(() -> phone.email(),"t1").start();

        Thread.sleep(100);

        //new Thread(() -> phone.SMS(),"t2").start();
        new Thread(() -> phone2.SMS(),"t2").start();
        //new Thread(() -> phone.sayHello(),"t2").start();

    }
}
