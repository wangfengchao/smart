package com.smart.algorithm.leetcode;

/**
 *
 * Created by Administrator on 2017/4/15.
 */
public class ReverseInteger_7 {

    public static void main(String[] args) {
        long result = reverse(1230000001);
        System.out.println(result);
    }

    public static int reverse(int x) {
        long ans = 0;
        final long MAX_VALUE = 0x7fffffff;
        final long MIN_VALUE = 0x80000000;
        while (x != 0) {
            ans = ans * 10 + (x % 10);
            x /= 10;
        }
        if (MAX_VALUE <= ans || MIN_VALUE >= ans) {
            ans = 0;
        }

        return (int)ans;
    }

}
