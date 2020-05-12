package com.ccs.thread.communication;

public class SynchronizedSequence2Demo {
    static Thread t1 = null, t2 = null;
    static volatile  boolean startt2 = true; //t2先运行
    public static void main(String[] args) {
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();
        Object obj = new Object();
        t1 = new Thread(() -> {
            synchronized (obj) {
                //大部分情况是线程1先执行，通过下面的循环等待线程2先执行，
                //如果线程1先执行，则释放锁，同时处于等待队列，直到notify后唤醒。线程2因为不是处于休眠状态因此可以获取锁
                //这里要循环吗？源代码是循环的
                if(startt2 == true){
                    try {
                        obj.wait();//使用wait、notify等必须在synchronized内部，否则报错IllegalMonitorStateException
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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
