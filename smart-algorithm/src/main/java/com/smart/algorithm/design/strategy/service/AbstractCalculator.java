package com.smart.algorithm.design.strategy.service;

/**
 * 辅助抽象类
 * Created by fc.w on 2017/9/5.
 */
public abstract class AbstractCalculator {

    public int[] split(String exp, String opt) {
        String[] arr = exp.split(opt);
        int[] arrInt = new int[2];
        arrInt[0] = Integer.parseInt(arr[0]);
        arrInt[1] = Integer.parseInt(arr[1]);

        return arrInt;
    }

}
