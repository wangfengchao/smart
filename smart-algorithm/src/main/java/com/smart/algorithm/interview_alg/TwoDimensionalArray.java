package com.smart.algorithm.interview_alg;

/**
 * 二维数组（N*N），沿对角线方向，从右上角打印到左下角
 * 主要是要找到数组下标的规律，先考虑一共生成几组结果，然后找到每组结果的规律//规律是第 K的 列号-行号=size-1-k
 * Created by fc.w on 2017/7/4.
 */
public class TwoDimensionalArray {

    public static void main(String[] args) {
        int[][] data = new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10,11,12},
                {13,14,15,16}};

        int size = data.length;
        // 一共生成几行结果
        int len = 2 * size - 1;

        for (int k = 0; k < len; k++) {
            // 行
            for (int i = 0; i < size; i++) {
                // 列
                for (int j = 0; j < size; j++) {
                    if (j - i == size - 1 - k) {
                        System.out.print(data[i][j] +",  ");
                    }
                }
            }
        }

    }

}
