package com.smart.algorithm.leetcode.string;

/**
 * 验证回文字符串是比较常见的问题，所谓回文，就是一个正读和反读都一样的字符串，比如“level”或者“noon”等等就是回文串。
 * 但是这里，加入了空格和非字母数字的字符，增加了些难度，但其实原理还是很简单：只需要建立两个指针，left和right, 分别从字符的开头和结尾处开始遍历整个字符串，
 * 如果遇到非字母数字的字符就跳过，继续往下找，直到找到下一个字母数字或者结束遍历，如果遇到大写字母，就将其转为小写。等左右指针都找到字母数字时，
 * 比较这两个字符，若相等，则继续比较下面两个分别找到的字母数字，若不相等，直接返回false.
 *  时间复杂度为O(n), 代码如下：
 *
 *  采用二分法，一个指针从左边遍历，一个从右边遍历，跳过非字母和非数字，当遍历到中点依然相同那就是回文
 * Created by fc.w on 2017/8/18.
 */
public class ValidPalindrome_125 {

    public static void main(String[] args) {
        ValidPalindrome_125 v = new ValidPalindrome_125();
        System.out.println(v.isPalindrome("Level"));
    }

    /**
     * 判断是否是回文
     * @param s
     * @return
     */
    public boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left <= right) {
            if (! isAlphanumeric(s.charAt(left))) {
                left++;
                continue;
            }
            if (! isAlphanumeric(s.charAt(right))) {
                right--;
                continue;
            }
            if (Character.toLowerCase(s.charAt(left)) == Character.toLowerCase(s.charAt(right))) {
                left++;
                right--;
                continue;
            }
            return false;
        }

        return true;
    }

    /**
     *
     * @param c
     * @return
     */
    public boolean isAlphanumeric(char c) {
        if (( c >= 'a' && c <= 'z' ) || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) return true;
        return  false;
    }


}
