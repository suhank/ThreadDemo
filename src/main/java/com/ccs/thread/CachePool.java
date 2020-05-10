package com.ccs.thread;

import java.util.concurrent.Executors;

import static java.util.concurrent.Executors.*;

import java.util.concurrent.ExecutorService;

public class CachePool {


    public static void main(String[] args) {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            final int index = i;
//            try {
//                Thread.sleep(1 * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            cachedThreadPool.execute(new Runnable() {
                public void run() {              
                	   try {
                         Thread.sleep(10 * 1000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                    System.out.println(index);
                }
            });
        }
        
     
    }
}

