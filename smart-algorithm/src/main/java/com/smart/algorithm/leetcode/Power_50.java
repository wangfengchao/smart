package com.smart.algorithm.leetcode;

/**
 * 使用倍增发 快速幂
 * Created by fc.w on 2017/5/5.
 */
public class Power_50 {

    public static void main(String[] args) {
        System.out.println(myPow(5, 5));
    }

    public static double myPow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        double ans = myPow(x, n/2);
        if (n % 2 == 0) {
            return ans * ans;
        } else {
            return ans * ans * x;
        }
    }
}
