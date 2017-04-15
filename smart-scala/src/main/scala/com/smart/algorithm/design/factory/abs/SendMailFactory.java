package com.smart.algorithm.design.factory.abs;

import com.smart.algorithm.design.factory.impl.MailSender;
import com.smart.algorithm.design.factory.impl.Sender;

/**
 * 工厂实现
 * Created by  fc.w on 2017/2/24.
 */
public class SendMailFactory implements Producer {

    public Sender produce() {
        return new MailSender();
    }

}
