package com.smart.algorithm.leetcode;

/**
 * Created by fc.w on 2017/4/25.
 */
public class PowerOfThree_326 {

    public static void main(String[] args) {
        System.out.println(powerOfThreeCycle(9));
        System.out.println(powerOfThreeBit(5));
        System.out.println(powerOfThreeMathematics(5));
    }

    /**
     * 循环
     *    1. 需要检验n != 0, 因为n == 0，while循环永远不会结束；
     *    2. n 不能是负数，这样没有意义。
     * @param n
     * @return
     */
    private static boolean powerOfThreeCycle(int n ) {
        if (n < 1) return false;
        while (n % 3 == 0) {
            n /= 3;
        }

        return n == 1;
    }

    /**
     *
     * @param n
     */
    private static boolean powerOfThreeBit(int n) {
        return Integer.toString(n, 3).matches("^10*$");
    }

    /**
     * 数学
     * @param n
     * @return
     */
    private static boolean powerOfThreeMathematics(int n) {
        return (Math.log10(n) / Math.log10(3)) % 1 == 0;
    }

}
