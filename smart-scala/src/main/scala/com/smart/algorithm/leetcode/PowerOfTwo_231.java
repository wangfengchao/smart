package com.smart.algorithm.leetcode;

/**
 * 判断是否为2的幂
 * Created by Administrator on 2017/4/16.
 */
public class PowerOfTwo_231 {

    public static void main(String[] args) {
        System.out.println("递归方式:   " + isPowerOfTwo_Recursion(8));
        System.out.println("二进制方式：" + isPowerOfTwo_Bit(8));
    }

    /**
     * 方法一：
     *     递归或循环，如果一个数是2的幂，则除以2肯定能整除，而且商肯定也是2的幂。
     * @param n
     * @return
     */
    public static boolean isPowerOfTwo_Recursion(int n) {
        if (n == 1) return true;
        if (n >= 2 && (n % 2 == 0)) {
            return isPowerOfTwo_Recursion(n / 2);
        }
        return false;
    }

    /**
     * 方法3：
     *     位操作，如果一个数是2的幂，其2进制表示位置只有最高位1，则(n&n-1)==0
     * @param n
     * @return
     */
    public static boolean isPowerOfTwo_Bit(int n) {
        if (n <= 0) return false;
        return (n & (n - 1)) == 0;
    }

}
