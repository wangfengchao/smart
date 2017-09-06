package com.smart.algorithm.leetcode.math;

/**
 * Created by fc.w on 2017/8/25.
 */
public class IntegerToRoman_12 {

    public String intToRoman(int num) {
        String res = "";
        char roman[] = {'M', 'D', 'C', 'L', 'X', 'V', 'I'};
        int value[] = {1000, 500, 100, 50, 10, 5, 1};

        for (int n = 0; n < 7; n += 2) {
            int x = num / value[n];
            if (x < 4) {
                for (int i = 1; i <= x; ++i) res += roman[n];
            } else if (x == 4) res = res + roman[n] + roman[n - 1];
            else if (x > 4 && x < 9) {
                res += roman[n - 1];
                for (int i = 6; i <= x; ++i) res += roman[n];
            }
            else if (x == 9) res = res + roman[n] + roman[n - 2];
            num %= value[n];
        }

        return res;
    }

    public static void main(String[] args) {
        IntegerToRoman_12 i = new IntegerToRoman_12();
        System.out.println(i.intToRoman(1900));
    }

}
