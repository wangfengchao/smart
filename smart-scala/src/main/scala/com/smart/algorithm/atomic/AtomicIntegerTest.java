package com.smart.algorithm.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by  fc.w on 2017/1/12.
 */
public class AtomicIntegerTest {

    static AtomicInteger ai = new AtomicInteger(1);

    public static void main(String[] args) {
        System.out.println(ai.getAndIncrement());
        System.out.println(ai.get());
    }
}
