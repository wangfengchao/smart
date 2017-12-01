package com.smart.algorithm.str;

/**
 * Created by fc.w on 2017/10/27.
 */
public class MaxSubString {

    public static String maxSubstring(String str1, String str2) {

        if (str1 == null || str2 == null) {
            return "";
        }

        if (str1.length() == 0 || str2.length() == 0) {
            return "";
        }

        String maxStr = "";
        String minStr = "";
        if (str1.length() < str2.length()) {
            maxStr = str2;
            minStr = str1;
        } else {
            maxStr = str1;
            minStr = str2;
        }

        String current = "";
        for (int i = 0; i < minStr.length(); i++) {
            for (int begin = 0, end = minStr.length() - i; end <= minStr.length(); begin++, end++) {
                current = minStr.substring(begin, end);
                if (maxStr.contains(current)) {
                    return current;
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {
        String strOne = "abcdefg";
        String strTwo = "adefgwgeweg";
        String result = maxSubstring(strOne, strTwo);
        System.out.println(result);
    }


}
