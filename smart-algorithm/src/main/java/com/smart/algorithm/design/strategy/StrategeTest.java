package com.smart.algorithm.design.strategy;

import com.smart.algorithm.design.strategy.impl.Plus;
import com.smart.algorithm.design.strategy.service.ICalculator;

/**
 * Created by fc.w on 2017/9/5.
 */
public class StrategeTest {

    public static void main(String[] args) {
        String exp = "2+8";
        ICalculator iCalculator = new Plus();
        int re = iCalculator.calculate(exp);
        System.out.println(re);
    }

}
