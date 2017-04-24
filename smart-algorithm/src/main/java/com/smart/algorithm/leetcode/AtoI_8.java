package com.smart.algorithm.leetcode;

/**
 * Created by Administrator on 2017/4/16.
 */
public class AtoI_8 {

    public static void main(String[] args) {
        System.out.println(myAtoi("234"));
    }

    private static int myAtoi(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        // 如果字符串以空格开始
        // 字符串下标
        int start = 0;
        // 表示字符串是否是正数，
        boolean positive = true;

        if (str.charAt(start) == ' ') {
            while (str.charAt(start) == ' ') {
                start++;
                // 判断字符串是否都是空格
                if (start >= str.length()) {
                    return 0;
                }
            }
        }

        //如果第一个字符是 - ，证明是负数
        if (str.charAt(start) == '-') {
            positive = false;
            start++;
        }else if (str.charAt(start) == '+') {
            start++;
        } else if (str.charAt(start) >= '0' || str.charAt(start) <= '9') {
            return cal(str, start, positive);
        } else {
            return 0;
        }

        // 第一个非空白字符是+或者-但也是最后一个字符
        if (str.charAt(start) > '9' || str.charAt(start) < '0') {
            return 0;
        } else {
            return cal(str, start, positive);
        }

    }

    private static int cal(String str, int start, boolean positive) {
        long result = 0;
        while (start < str.length() && str.charAt(start) >= '0' && str.charAt(start) <= '9') {
            result = result * 10 + (str.charAt(start) - '0');
            //如果是正数
            if (positive) {
                if (result > Integer.MAX_VALUE) {
                    return Integer.MAX_VALUE;
                }
            } else {
                if (result < Integer.MIN_VALUE) {
                    return Integer.MIN_VALUE;
                }
            }

            start++;
        }

        if (positive) {
            return (int)result;
        } else {
            return (int)-result;
        }
    }

}
