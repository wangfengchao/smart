package com.dodoca.smart.design.factory;

import com.dodoca.smart.design.factory.abs.Producer;
import com.dodoca.smart.design.factory.abs.SendMailFactory;
import com.dodoca.smart.design.factory.impl.Sender;

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
