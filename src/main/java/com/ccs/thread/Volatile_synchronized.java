package com.ccs.thread;

    public class Volatile_synchronized implements Runnable {

        private volatile int count = 100;

        public /*synchronized*/ void run() {
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }

        public static void main(String[] args) {
            Volatile_synchronized t = new Volatile_synchronized();
            for(int i=0; i<100; i++) {
                new Thread(t, "THREAD" + i).start();
            }
        }

    }
