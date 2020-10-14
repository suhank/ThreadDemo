package com.ccs.thread.communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    static Thread t1 = null, t2 = null;
    static volatile  boolean startt2 = true; //t2先运行
    public static void main(String[] args) {
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();
        Lock lock = new ReentrantLock();
        Condition conditionT1 = lock.newCondition();
        Condition conditionT2 = lock.newCondition();

        t1 = new Thread(() -> {
            lock.lock();//手动上锁
            try {
                if(startt2 == true){
                    System.out.println("执行线程1等待");
                    conditionT1.await(); //线程1的用conditionT1先阻塞，此时也会释放锁了等于unlock了
                    System.out.println("执行线程1等待结束");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (char c : aI) {
                System.out.println(c);
                try {
                    conditionT2.signal(); //通过condition指定是具体哪个线程唤醒
                    conditionT1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//            conditionT2.signal();
            lock.unlock();
        }, "t1");

        t2 = new Thread(() -> {
            lock.lock();//手动上锁
            startt2 = false;
            for (char c : aC) {
                System.out.println(c);
                try {
                    conditionT1.signal(); //通过condition指定是具体哪个线程唤醒
                    System.out.println("唤醒执行线程1");
                    conditionT2.await();
                    System.out.println("执行线程2等待结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //t2线程先执行，最后一次的时候t1线程在等待，所以需要在t2调用t1的condition的signal
           conditionT1.signal();
            lock.unlock();
        }, "t2");
        t1.start();
        t2.start();
    }
}
