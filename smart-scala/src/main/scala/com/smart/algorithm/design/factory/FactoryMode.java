package com.smart.algorithm.design.factory;

import com.smart.algorithm.design.factory.impl.MailSender;
import com.smart.algorithm.design.factory.impl.Sender;
import com.smart.algorithm.design.factory.impl.SmsSender;

/**
 * 静态工厂方法模式
 * Created by  fc.w on 2017/2/24.
 */
public class FactoryMode {

    public static Sender produceMail() {
       return new MailSender();
    }

    public static Sender produceSms() {
        return new SmsSender();
    }

}
