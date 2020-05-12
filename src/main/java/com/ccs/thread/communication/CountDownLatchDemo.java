package com.ccs.thread.communication;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    static Thread t1 = null, t2 = null;
    static CountDownLatch latch = new CountDownLatch(1);
    public static void main(String[] args) {
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();
        Object obj = new Object();
        t1 = new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (obj) {
                for (char c : aI) {
                    System.out.println(c);
                    try {
                        obj.notify();
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                obj.notify();
            }

        }, "t1");

        t2 = new Thread(() -> {
            synchronized (obj) {
                latch.countDown();
                for (char c : aC) {
                    System.out.println(c);
                    try {
                        obj.notify();
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                obj.notify();
            }
        }, "t2");
        t1.start();
        t2.start();
    }
}
