package com.smart.algorithm.leetcode.dynamicProgramming;

import java.util.ArrayList;
import java.util.List;

/**
 * 题意分析： 对输入的字符串划分为一组回文字符串。
   解题思路：首先用动态规划的方法生成标志回文字符串的数组，partitioning_map[i][j]=1的话，表明：string[i..j]是一个回文字符串。
     其中一个知识点是：划分的时候判断a[i][j]是否是回文串，需要用到：a[i+1][j-1]的值，所以生成标志回文字符串的数组的时候外层循环要从 s.length()-1 开始逐次递减。
     其次根据生成好的partitioning_map用dfs对数组进行划分。

 * Created by fc.w on 2017/9/27.
 */
public class PalindromePartitioning_131 {

    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<List<String>>();
        List<String> array = new ArrayList<String>();
        char[][] palindrome_map = new char[s.length()][s.length()];
        if (s == null || s.length() == 0) {
            result.add(array);
            return result;
        }

        dp(s, palindrome_map);
        dfs(s, 0, palindrome_map, array, result);

        return result;
    }

    /**
     * 生成标志回文字符串的数组，partitioning_map[i][j]=1的话，表明：string[i..j]是一个回文字符串
     * @param s
     * @param palindrome_map
     */
    public void dp(String s, char[][] palindrome_map) {
        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = i; j < s.length(); j++) {
                if (i == j) {
                    palindrome_map[i][j] = 1;
                } else {
                    if (s.charAt(i) == s.charAt(j)) {
                        if (j == i + 1 || palindrome_map[i + 1][j - 1] == 1) {
                            palindrome_map[i][j] = 1;
                        }
                    }
                }
            }
        }
    }

    /**
     * 根据生成好的回文标记数组对字符串进行划分
     * @param s
     * @param begin
     * @param palindrome_map
     * @param array
     * @param result
     */
    public void dfs(String s, int begin, char[][] palindrome_map, List<String> array, List<List<String>> result) {
        if (begin == s.length()) {
            result.add(array);
            return;
        }

        for (int i = begin; i < s.length(); i++) {
            if (palindrome_map[begin][i] == 1) {
                List<String> tmp = new ArrayList<String>(array);
                tmp.add(s.substring(begin, i + 1));
                dfs(s, i + 1, palindrome_map, tmp, result);
            }
        }
    }


    public static void main(String[] args) {
        PalindromePartitioning_131 p = new PalindromePartitioning_131();
        List<List<String>> re = p.partition("aab");
        for (List<String> l : re) {
            System.out.println("--------------------------");
            for (String s : l) {
                System.out.println(s);
            }
        }

    }

}
