package com.ccs.thread.communication;

/**
 * 增加线程执行顺序
 */

public class SynchronizedSequenceDemo {
    static Thread t1 = null, t2 = null;
    static volatile  boolean startt2 = true; //t2先运行
    public static void main(String[] args) {
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();
        Object obj = new Object();
        t1 = new Thread(() -> {
            while(startt2 == true){

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
                startt2 = false;
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
