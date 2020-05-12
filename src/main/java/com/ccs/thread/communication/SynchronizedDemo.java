package com.ccs.thread.communication;

import java.util.concurrent.locks.LockSupport;

public class SynchronizedDemo {
    static Thread t1 = null, t2 = null;
    public static void main(String[] args) {
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();
        Object obj = new Object();
        t1 = new Thread(()->{
            synchronized (obj){
                //进入synchronize就是获取锁了? 看看控制线程顺序的代码是在synchronized里面的
                for(char c : aI){
                    System.out.println(c);
                    try {
                        obj.notify();
                        //先要notify，再执行wait，否则就卡主了，不执行notify了
                        //通知等待obj锁的线程执行，如果有多个等待线程，
                        // 无法确定是哪一个执行，如果需要指定可以使用reentrncelock的condition
                        obj.wait(); //释放锁，同时线程处于等待获取锁状态，
                        // 代码处于synchronize包围的地方需要锁，并不是把synchronize放在循环里面

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //Exception in thread "t1" java.lang.IllegalMonitorStateException
                //首先你要了解这个异常为什么会抛出，这个异常会在三种情况下抛出：
                //1>当前线程不含有当前对象的锁资源的时候，调用obj.wait()方法;
                //2>当前线程不含有当前对象的锁资源的时候，调用obj.notify()方法。
                //3>当前线程不含有当前对象的锁资源的时候，调用obj.notifyAll()方法。
                //下面的代码需要放在synchronized里面
                obj.notify();//因为有最后一个线程肯定会处于wati状态，因此最后需要进行notify让线程结束
            }

        },"t1");

        t2 = new Thread(()->{
            //这里不能保证t1还是t2谁先获取锁，如何控制可以使用重录锁
            //假设t1先获取锁，则t2处于等待，直到获取notify时候进入获取锁状态，也要占用锁的进行wait之后才能获取
            synchronized (obj){
                for(char c : aC){
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

        },"t2");
        t1.start();
        t2.start();
    }

}
