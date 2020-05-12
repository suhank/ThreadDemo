package com.ccs.thread.communication;

public class VolatileDemo{

    enum RedayToRun{T1 , T2};
    static volatile  RedayToRun r = RedayToRun.T1;
    static Thread t1 = null, t2 = null;
    public static void main(String[] args) {
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        t1 = new Thread(()->{
            for(char c : aI){
                while(r != RedayToRun.T1){

                }
                System.out.println(c);
                r = RedayToRun.T2;
            }
        },"t1");

        t2 = new Thread(()->{
            for(char c : aC){
                while(r != RedayToRun.T2){

                }
                System.out.println(c);
                r = RedayToRun.T1;
            }
        },"t2");
        t1.start();
        t2.start();
    }
}
