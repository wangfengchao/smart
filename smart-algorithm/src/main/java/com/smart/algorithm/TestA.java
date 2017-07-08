package com.smart.algorithm;

import java.util.Arrays;

/**
 * Created by  fc.w on 2017/2/7.
 */
public class TestA {

    public static void main(String[] args) {
        int[][] data = new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10,11,12},
                {13,14,15,16}};
        System.out.println(data.length);
        printArr(data, data.length);
    }

    private static void printArr(int[][] arr, int n){
        for (int i = 1; i < 2*n; i++) {
            if (i <= n){
                for (int j = 0; j < i; j++) {
                    System.out.print(arr[j][n-i+j] + ", ");
                }
            }else {
                for (int j = 0; j < 2*n-i; j++) {
                    System.out.print(arr[i-n+j][j] + ", ");
                }
            }

        }
    }
}
