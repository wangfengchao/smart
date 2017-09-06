package com.smart.algorithm.design.chain;

/**
 * Created by fc.w on 2017/9/6.
 */
public class MyHandler extends AbstractHandler implements Handler {

    private String name;

    public MyHandler(String name) {
        this.name = name;
    }

    public void operator() {
        System.out.println(name + "deall!");
        if (getHandler() != null) {
            getHandler().operator();
        }
    }


}
