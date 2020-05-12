package com.ccs.thread.communication;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipeStreamDemo {
    static Thread t1 = null, t2 = null;

    public static void main(String[] args) throws IOException {
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();
        PipedInputStream input1 = new PipedInputStream();
        PipedInputStream input2 = new PipedInputStream();
        PipedOutputStream out1 = new PipedOutputStream();
        PipedOutputStream out2 = new PipedOutputStream();
        //下面两种顺序无所谓
//        out1.connect(input1);
//        out2.connect(input2);
        input1.connect(out1);
        input2.connect(out2);
        String msg = "turn";
        byte[] buffer = new byte[4];
        t1 = new Thread(() -> {

            for (char c : aI) {
                try {
                    System.out.println(c);
                    out2.write(msg.getBytes());
                    input1.read(buffer);// 卡主，等待输出管道的信息

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, "t1");

        t2 = new Thread(() -> {
            for (char c : aC) {
                try {
                    input2.read(buffer);
                    System.out.println(c);
                    out1.write(msg.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, "t2");
        t1.start();
        t2.start();
    }
}
