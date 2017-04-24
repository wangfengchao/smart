package com.smart.algorithm.leetcode;

/**
 * Created by  fc.w on 2017/1/12.
 */
public class ReverseString {

    public static void main(String[] args) {
        System.out.println(reverseString("hello"));
        System.out.println(reverseString1("hello"));
        System.out.println(reverseString2("hello"));
        System.out.println(reverseString3("hello"));
    }

    public static String reverseString(String s) {
        String re = "";
        char[] c = s.toCharArray();
        for (int i = c.length - 1; i >= 0; i--) {
            re += c[i];
        }
        return re;
    }


    public static String reverseString1(String s) {
        String result = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            result += s.charAt(i);
        }
        return result;
    }

    public static String reverseString2(String s) {
        StringBuffer sb = new StringBuffer(s);
        return sb.reverse().toString();
    }

    public static String reverseString3(String s) {
        char[] c = s.toCharArray();
        int len = c.length / 2;
        char temp;
        for (int i = 0; i < len; i++) {
            temp = c[c.length- 1 - i];
            c[c.length- 1 - i] = c[i];
            c[i] = temp;
        }
        return new String(c);
    }
}
