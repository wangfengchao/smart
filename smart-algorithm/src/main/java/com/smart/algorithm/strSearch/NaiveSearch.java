package com.smart.algorithm.strSearch;

/**
 * 在一长字符串中找出其是否包含某子字符串。
 * 首先当然还是简单算法，通过遍历来检索所有的可能：
 *
 * 时间复杂度为 Θ((n-m+1) m)
 * Created by fc.w on 2017/9/6.
 */
public class NaiveSearch {

    public static int naiveSearch(String content, String sub) {
        for (int i = 0; i < (content.length() - sub.length()); i++) {
            boolean found = true;
            for (int j = 0; j <sub.length(); j++) {
                if (content.charAt(i + j) != sub.charAt(j)) {
                    found = false;
                    break;
                }
            }

            if (found) return i;
        }

        return -1;
    }

    public static void main(String[] args) {
        System.out.println(naiveSearch("helloworld", "wol"));
    }

}
