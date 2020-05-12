package com.ccs.thread.communication;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {
    static Thread t1 = null, t2 = null;
    public static void main(String[] args) {
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        t1 = new Thread(()->{
            for(char c : aI){
                System.out.println(c);
                //unpark可以先执行，不需要先执行park。如果unpark已经执行了，后面线程park的时候直接就不需要等待了
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        },"t1");

        t2 = new Thread(()->{
            for(char c : aC){
                LockSupport.park();
                System.out.println(c);
                LockSupport.unpark(t1);
            }
        },"t2");
        t1.start();
        t2.start();
    }
}
