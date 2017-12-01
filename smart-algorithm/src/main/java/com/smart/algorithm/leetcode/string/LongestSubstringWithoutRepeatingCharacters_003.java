package com.smart.algorithm.leetcode.string;

/**
 * 无重复字符的最长子串
 给定一个字符串，找其无重复字符的最长子串的长度。
 例如： "abcabcbb"的无重复字符的最长子串是"abc"，长度为3。 "bbbbb"的无重复字符的最长子串是"b"，长度为1
 * Created by fc.w on 2017/10/16.
 */
public class LongestSubstringWithoutRepeatingCharacters_003 {

    public int lengthOfLongestSubstring(String s) {
        int maxLen = 0;
        int repeatIndex = 0;
        int size = s.length();
        char[] c = s.toCharArray();

        for (int i = 0; i < size; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (c[i] == c[j]) {
                    if (j > repeatIndex) {
                        repeatIndex = j;
                    }
                    break;
                }
            }

            if (maxLen < i - repeatIndex) {
                maxLen = i - repeatIndex;
            }

        }

        return maxLen;
    }

    public static void main(String[] args) {
        LongestSubstringWithoutRepeatingCharacters_003 l = new LongestSubstringWithoutRepeatingCharacters_003();
        System.out.println(l.lengthOfLongestSubstring("abcabcbb"));
    }

}
