package com.smart.algorithm.leetcode.dynamicProgramming;

/**
 * 题意：台阶总数为n，每次爬1阶或者2阶层，求爬完台阶的不同的方法数。

 第一次爬1阶，则剩下n-1阶；
 第一次爬2阶，则剩下n-2阶；
 递推表达式为：
 F(n) = F(n-1) + F(n-2)，F(1) = 1, F(2) = 2;

 由于F(n) = F(n-1) + F(n-2) = (F(n-2) + F(n-3)) + (F(n-3) + F(n-4))=.....
 由于计算结果没有保存，越往后，重复计算越多，考虑使用动态规划，将F(n-1)和F(n-2) 保存下来。

 * Created by fc.w on 2017/8/8.
 */
public class ClimbingStairs_70 {

    public static int climbStairs(int n) {
        if (n < 3) return n;

        int beforePre = 1;
        int pre = 2;
        int curr = 3;

        for (int i = 3; i <= n; i++) {
            curr = pre + beforePre;
            beforePre = pre;
            pre = curr;
        }

        return curr;
    }

    public static void main(String[] args) {
        System.out.println(climbStairs(3));
    }

}
