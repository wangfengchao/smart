package com.smart.algorithm.design.bridge;

/**
 * Created by fc.w on 2017/9/5.
 */
public class MyBridge extends Bridge {

    @Override
    public void method() {
        getSourceable().method();
    }
}
