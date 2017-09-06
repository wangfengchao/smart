package com.smart.algorithm.design.chain;

/**
 * 责任链模式
 * Created by fc.w on 2017/9/6.
 */
public class HandlerTest {

    public static void main(String[] args) {
        MyHandler m1 = new MyHandler("h1");
        MyHandler m2 = new MyHandler("h2");
        MyHandler m3 = new MyHandler("h3");

        m1.setHandler(m2);
        m2.setHandler(m3);

        m1.operator();
    }

}
