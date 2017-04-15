package com.smart.algorithm.design.factory.impl;

/**
 * Created by  fc.w on 2017/2/24.
 */
public class SmsSender implements Sender {

    public void send() {
        System.out.println("this is sms sender!");
    }

}
