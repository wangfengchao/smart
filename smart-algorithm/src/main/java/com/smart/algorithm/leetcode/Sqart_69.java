package com.smart.algorithm.leetcode;

/**
 * 二分法
 * Created by fc.w on 2017/4/28.
 */
public class Sqart_69 {

    public static void main(String[] args) {
        System.out.println(mySqrt(9));
    }

    private static boolean guess(long x, long y) {
        return (long) x * x <= y;
    }

    private static int mySqrt(int y) {
        long L = 0, R = (long) y + 1;
        long ans = 0;
        while (L < R) {
            long mid = (L + R) / 2;
            if (guess(mid, y)) {
                ans = mid;
                L = mid + 1;
            } else {
                R = mid;
            }

        }

        return (int) ans;
    }

}
