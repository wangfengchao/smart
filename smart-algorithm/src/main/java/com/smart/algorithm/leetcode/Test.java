package com.smart.algorithm.leetcode;

/**
 * Created by Administrator on 2017/4/17.
 */
public class Test {

    public static void main(String[] args) {
        // 10011100001111
        System.out.println(fun(9999));
    }

    private static int fun(int nValue) {
        int nCount = 0;
        while (nValue > 0) {
            nValue = nValue&(nValue - 1);
            ++nCount;
        }

        return nCount;
    }

}
