package com.ccs.thread.communication;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueDemo {
    static BlockingQueue<String> q1 = new ArrayBlockingQueue<String>(1);
    static BlockingQueue<String> q2 = new ArrayBlockingQueue<String>(1);
    public static void main(String[] args) {
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        new Thread(()->{
            for(char c : aI){
                try {
                    System.out.println(c);
                    q1.put("ok");
                    q2.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        },"t1").start();

        new Thread(()->{
            for(char c : aC){
                //take会阻塞
                try {
                    q1.take();
                    System.out.println(c);
                    q2.put("ok");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        },"t2").start();
    }
}
