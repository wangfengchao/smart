package com.smart.algorithm.leetcode;

/**
 * 计算阶乘末尾有多少个0
 * 1的阶乘：1
 2的阶乘：2
 3的阶乘：6
 4的阶乘：24
 5的阶乘：120
 6的阶乘：720
 7的阶乘：5040
 8的阶乘：40320
 9的阶乘：362880
 * 5! = 120             末尾有1个0
 * 10的阶乘 = 3628800  末尾有2个0
 * 11！ = 39 916 800    末尾有2个0
 * 15！= 1307674368000 末尾有3个0
 *
 * 规律：
 * 因为每乘到5和10的倍数,才会增加一个0.
 *
 * Created by Administrator on 2017/4/25.
 */
public class FactorialTrailingZeros_172 {

    public static void main(String[] args) {
        System.out.println(trailingZeroes(12));
    }


    private static int trailingZeroes(int n) {
        int sum = 0;
        while (n > 0) {
            sum += n / 5;
            n /= 5;
        }
        return sum;
    }

}
