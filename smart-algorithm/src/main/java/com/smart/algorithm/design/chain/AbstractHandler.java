package com.smart.algorithm.design.chain;

/**
 * Created by fc.w on 2017/9/6.
 */
public abstract class AbstractHandler {

    private Handler handler;

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
