package com.smart.algorithm.design.factory;

import com.smart.algorithm.design.factory.abs.SendMailFactory;
import com.smart.algorithm.design.factory.impl.Sender;
import com.smart.algorithm.design.factory.abs.Producer;

/**
 * 抽象工厂模式
 * Created by  fc.w on 2017/2/24.
 */
public class AbsFactory {

    public static void main(String[] args) {
        Producer prod = new SendMailFactory();
        Sender sender = prod.produce();
        sender.send();
    }

}
