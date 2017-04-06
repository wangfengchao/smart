package com.smart.algorithm.design.factory.abs;

import com.smart.algorithm.design.factory.impl.Sender;
import com.smart.algorithm.design.factory.impl.SmsSender;

/**
 * Created by  fc.w on 2017/2/24.
 */
public class SendSmsFactory implements Producer {
    public Sender produce() {
        return new SmsSender();
    }
}
