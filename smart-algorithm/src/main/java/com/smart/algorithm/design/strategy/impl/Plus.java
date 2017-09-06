package com.smart.algorithm.design.strategy.impl;

import com.smart.algorithm.design.strategy.service.AbstractCalculator;
import com.smart.algorithm.design.strategy.service.ICalculator;

/**
 * Created by fc.w on 2017/9/5.
 */
public class Plus extends AbstractCalculator implements ICalculator {
    public int calculate(String exp) {
        int arr[] = split(exp, "\\+");
        return arr[0] + arr[1];
    }
}
