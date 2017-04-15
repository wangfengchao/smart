package com.smart.algorithm.design.factory.abs;

import com.smart.algorithm.design.factory.impl.Sender;

/**
 * 抽象工厂模式
 * Created by  fc.w on 2017/2/24.
 */
public interface Producer {

    Sender produce();

}
