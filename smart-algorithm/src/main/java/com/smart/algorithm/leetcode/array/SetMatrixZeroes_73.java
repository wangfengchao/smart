package com.smart.algorithm.leetcode.array;

/**
 * 这道题中说的空间复杂度为O(mn)的解法自不用多说，直接新建一个和matrix等大小的矩阵，然后一行一行的扫，只要有0，就将新建的矩阵的对应行全赋0，行扫完再扫列，
 * 然后把更新完的矩阵赋给matrix即可，这个算法的空间复杂度太高。将其优化到O(m+n)的方法是，用一个长度为m的一维数组记录各行中是否有0，用一个长度为n的一维数组记录各列中是否有0，最后直接更新matrix数组即可。
 * 这道题的要求是用O(1)的空间，那么我们就不能新建数组，我们考虑就用原数组的第一行第一列来记录各行各列是否有0.

 - 先扫描第一行第一列，如果有0，则将各自的flag设置为true
 - 然后扫描除去第一行第一列的整个数组，如果有0，则将对应的第一行和第一列的数字赋0
 - 再次遍历除去第一行第一列的整个数组，如果对应的第一行和第一列的数字有一个为0，则将当前值赋0
 - 最后根据第一行第一列的flag来更新第一行第一列

 就是相比用O(m+n)的空间，不额外加一行一列，就用第一行和第一列来存储哪行哪列有0。当然，这样做比较麻烦的是第一行第一列的原始信息，需要先保存下来。
 * Created by fc.w on 2017/8/10.
 */
public class SetMatrixZeroes_73 {

    public static void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        // 1. 标记第一行和第一列的第一个元素是否有0
        boolean firstRow = false;
        boolean firstCol = false;
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                firstCol = true;
                break;
            }
        }
        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == 0) {
                firstRow = true;
                break;
            }
        }

        // 2. 从第二行第二列还是遍历，如果遇到0，则把它所在行和列的第一个值设为0
        for (int i = 1; i < m; i++) {
            for (int j = 1; j  < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        // 3. 把第一列的0所在行都设为0，把第一行的0所在列都设为0
        for (int i = 1; i < m; i++) {
            if (matrix[i][0] == 0) {
                for (int j = 1; j < n; j++) {
                    matrix[i][j] = 0;
                }
            }
        }
        for (int j = 1; j < n; j++) {
            if (matrix[0][j] == 0) {
                for (int i = 1; i < m; i++) {
                    matrix[i][j] = 0;
                }
            }
        }

        // 4. 根据标记决定第一行和第一列是否全设为0
        if (firstRow) {
            for (int j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }

        if (firstCol) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }

    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 0}, {3, 0, 4}, {5, 6, 7}};
        setZeroes(matrix);
        int m = matrix.length;
        int n = matrix[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println(matrix[i][j]);
            }
        }

    }

}
