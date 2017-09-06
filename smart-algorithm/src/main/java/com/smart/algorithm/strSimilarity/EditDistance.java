package com.smart.algorithm.strSimilarity;

/**
 * 字符串相似度算法(编辑距离Levenshtein Distance)
 *
 * 由于需要利用LD矩阵，故空间复杂度为O(MN)。这个在两个字符串都比较短小的情况下，能获得不错的性能。
 * 不过，如果字符串比较长的情况下，就需要极大的空间存放矩阵。
 * http://blog.csdn.net/chndata/article/details/42552971
 * 例如：两个字符串都是20000字符，则LD矩阵的大小为：20000*20000*2=800000000Byte=800MB。
 * Created by fc.w on 2017/9/6.
 */
public class EditDistance{

    public static void main(String[] args) {
        System.out.println(getDistance("hello", "hella"));
    }

    public static float getDistance(String s1, String s2) {
        char[] sa;
        int n;
        int p[]; // 'previous' cost array, horizontally
        int d[]; // cost array, horizontally
        int _d[]; // placeholder to assist in swapping p and d

        sa = s1.toCharArray();
        n = sa.length;
        p = new int[n + 1];
        d = new int[n + 1];

        final int m = s2.length();
        if (n == 0 ||m == 0) {
            if (n ==m) {
                return 1;
            } else {
                return 0;
            }
        }

        // indexes into strings s and t
        int i; // iterates through s
        int j; // iterates through t
        char t_j; // jth character of t
        int cost; // cost

        for (i = 0;i <= n; i++) {
            p[i] =i;
        }

        for (j = 1;j <= m; j++) {
            t_j = s2.charAt(j - 1);
            d[0] = j;
            for (i = 1;i <= n; i++) {
                cost = sa[i - 1] == t_j ? 0 : 1;
                /* minimum of cell to the left+1, to the top+1, diagonally left
                and up +cost */
                d[i] = Math.min(Math.min(d[i - 1] + 1,p[i] + 1), p[i - 1]
                        + cost);
            }
            /* copy current distance counts to 'previous row' distance counts */
            _d = p;
            p = d;
            d = _d;
        }

        /* 相似度计算
        * 计算相似度公式：1 - 它们的距离 / 两个字符串长度的最大值。
        * */
        return 1.0f - ((float)p[n] / Math.max(s2.length(), sa.length));
    }
}
