package com.smart.algorithm.design.factory;

import com.smart.algorithm.design.factory.impl.Sender;

/**
 * Created by  fc.w on 2017/2/24.
 */
public class FactoryTest {

    public static void main(String[] args) {
        Sender send = FactoryMode.produceMail();
        send.send();
    }

}
