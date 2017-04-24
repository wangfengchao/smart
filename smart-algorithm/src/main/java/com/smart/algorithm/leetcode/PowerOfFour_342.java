package com.smart.algorithm.leetcode;

/**
 * 判断是否为4的幂
 * Created by Administrator on 2017/4/16.
 */
public class PowerOfFour_342 {

    public static void main(String[] args) {
        System.out.println(isPowerOfFour_Recursion(16));
    }

    /**
     * 方法一：
     *     递归或循环，如果一个数是4的幂，则除以4肯定能整除，而且商肯定也是4的幂。
     * @param n
     * @return
     */
    public static boolean isPowerOfFour_Recursion(int n) {
        if (n == 1) return true;
        if (n >= 4 && (n % 4 == 0)) {
            return isPowerOfFour_Recursion(n / 4);
        }
        return false;
    }

}
