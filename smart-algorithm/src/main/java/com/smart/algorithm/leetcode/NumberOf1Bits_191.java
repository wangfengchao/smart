package com.smart.algorithm.leetcode;

/**
 * 题目的要求很简单，找出一个整数对应的二进制格式中有多少个'1'
 *
 * 1. 求解一个整数的二进制表示法中有几个1
 *
 * 2. n为奇数(n的二进制表示的末位为1)
 *    n为偶数且不等于0(n的二进制表示的末位为0)
 *
 * n = 0x110100      n-1 = 0x110011     n&(n - 1) = 0x110000
   n = 0x110000      n-1 = 0x101111     n&(n - 1) = 0x100000
   n = 0x100000      n-1 = 0x011111     n&(n - 1) = 0x0
   看到这里已经得到了一种新的解法，n中本来有3个1，按照此种思路只需要循环3此即可求出最终结果

 * Created by Administrator on 2017/4/17.
 */
public class NumberOf1Bits_191 {

    public static void main(String[] args) {
        System.out.println(hammingWeight(0000000000000000000000000001111));
        System.out.println(hammingWeight(9999));
    }

    public static int hammingWeight(int n) {
        int ans = 0;
        while (n != 0) {
            n = n & (n - 1);
            ans++;
        }

        return ans;
    }

}
