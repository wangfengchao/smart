package com.smart.algorithm.leetcode.math;

/**
 * 【解析】
 题意：判断一个字符串是否是计算机合法数字。

 思路：把字符串分三段（小数点前、小数点与e/E之间部分、e/E之后），这三段都必须为纯数字组成的字符串。注意，第一部分和第三部分可以带符号（如+12.34e-56），第一部分和第二部分可以有一部分为空（如“.2"或"2."）。
 1 去掉首尾多余的空格；
 2 去掉开头的正负号；
 3 看有没有e或E，如果有那么e/E后面只能是整数；
 4 再看e前面的部分有没有小数点，小数点前后两部分都必须为整数。
 * Created by fc.w on 2017/8/1.
 */
public class ValidNumber_65 {

    public boolean isNumber(String s) {

        // 1. 去除空格,验证字符串是否是空
        s = s.trim();
        if (s.length() == 0) return false;

        // 2. 去掉开头正负号
        if (s.charAt(0) == '+' || s.charAt(0) == '-') {
            s = s.substring(1);
        }

        // 3. 判断字符串中是否有e或E
        int pose = s.indexOf("E") >= 0 ? s.indexOf("E") : s.indexOf("e");
        if (pose >= 0) {
            String poste = s.substring(pose + 1);
            if (poste.length() == 0) return false;
            // 去掉开头正负号
            if (poste.charAt(0) == '+' || poste.charAt(0) == '-') {
                poste = poste.substring(1);
            }
            if (! isPureDigit(poste)) return false;
            s = s.substring(0, pose);
        }

        // 4. e前面的部分有没有小数点，小数点前后两部分都必须为整数
        int posdot = s.indexOf('.');
        if (posdot >= 0) {
            String predot = s.substring(posdot + 1);
            String postdot = s.substring(0, posdot);
            if (predot.length() == 0) return isPureDigit(postdot);
            if (postdot.length() == 0) return isPureDigit(predot);

            return isPureDigit(predot) && isPureDigit(postdot);
        }

        return isPureDigit(s);
    }

    // 判断是否是数字
    public boolean isPureDigit(String s) {
        if (s.length() == 0) return false;
        for (int i = 0; i < s.length(); i++) {
            if (! Character.isDigit(s.charAt(i))) return false;
        }

        return true;
    }

    public static void main(String[] args) {
        String s = "2e10";
        ValidNumber_65 v = new ValidNumber_65();
        System.out.println(v.isNumber(s));
    }

}
