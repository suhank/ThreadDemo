package com.ccs.basic;

import java.util.BitSet;

public class BitSetDemo {

    public static void main(String[] args) {
        BitSet bitset = new BitSet();
//        bitset.set(10000005);
        bitset.set(64);
        bitset.set(90);
        int xx = bitset.cardinality();
        System.out.println(xx);
    }
}
