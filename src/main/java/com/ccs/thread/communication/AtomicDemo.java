package com.ccs.thread.communication;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {
    static AtomicInteger threadNo = new AtomicInteger(1);
    static Thread t1 = null, t2 = null;
    public static void main(String[] args) {
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        t1 = new Thread(()->{
            for(char c : aI){
                while(threadNo.get() != 1){

                }
                System.out.println(c);
                threadNo.set(2);
            }
        },"t1");

        t2 = new Thread(()->{
            for(char c : aC){
                while(threadNo.get() != 2){

                }
                System.out.println(c);
                threadNo.set(1);
            }
        },"t2");
        t1.start();
        t2.start();
    }
}
