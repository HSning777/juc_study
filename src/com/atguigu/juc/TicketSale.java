package com.atguigu.juc;

public class TicketSale {
    public static void main(String[] args) {
        Tickets tickets = new Tickets();

        new Thread(() -> { for (int i = 0; i <= 31; i++) tickets.sale(); },"A").start();
        new Thread(() -> { for (int i = 0; i <= 31; i++) tickets.sale(); },"B").start();
        new Thread(() -> { for (int i = 0; i <= 31; i++) tickets.sale(); },"C").start();
    }
}
