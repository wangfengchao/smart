package com.dodoca.smart.design.factory.abs;

import com.dodoca.smart.design.factory.impl.Sender;
import com.dodoca.smart.design.factory.impl.SmsSender;

/**
 * Created by  fc.w on 2017/2/24.
 */
public class SendSmsFactory implements Producer {
    public Sender produce() {
        return new SmsSender();
    }
}
