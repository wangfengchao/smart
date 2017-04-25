package com.smart.algorithm.leetcode;

/**
 * 非负整数各位相加
 * 中文：有一个非负整数num，重复这样的操作：对该数字的各位数字求和，
 * 对这个和的各位数字再求和……直到最后得到一个仅1位的数字（即小于10的数字）。
 * Created by Administrator on 2017/4/25.
 */
public class AddDigits_258 {

    public static void main(String[] args) {
        System.out.println(addDigits_1(38));
        System.out.println(addDigits_2(38));
    }

    /**
     * 1. 逐位相加直到小于10
     * @param num
     * @return
     */
    private static int addDigits_1(int num) {
        while (num >= 10) {
            num = (num / 10) + num %10;
        }
        return num;
    }

    /**
     * 假设输入的数字是一个5位数字num，则num的各位分别为a、b、c、d、e。
     * 有如下关系：num = a * 10000 + b * 1000 + c * 100 + d * 10 + e
     * 即：num = (a + b + c + d + e) + (a * 9999 + b * 999 + c * 99 + d * 9)
     * 因为 a * 9999 + b * 999 + c * 99 + d * 9 一定可以被9整除，因此num模除9的结果与 a + b + c + d + e 模除9的结果是一样的。
     * 对数字 a + b + c + d + e 反复执行同类操作，最后的结果就是一个 1-9 的数字加上一串数字，最左边的数字是 1-9 之间的，右侧的数字永远都是可以被9整除的。
     * 这道题最后的目标，就是不断将各位相加，相加到最后，当结果小于10时返回。因为最后结果在1-9之间，得到9之后将不会再对各位进行相加，
     * 因此不会出现结果为0的情况。因为 (x + y) % z = (x % z + y % z) % z，又因为 x % z % z = x % z，因此结果为 (num - 1) % 9 + 1，
     * 只模除9一次，并将模除后的结果加一返回。
     * @param num
     * @return
     */
    private static int addDigits_2(int num) {
        if (num == 0) return 0;
        return (num - 1) % 9 + 1;
    }

}
