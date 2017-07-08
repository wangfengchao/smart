package com.smart.algorithm.sort;

/**
 * Created by fc.w on 2017/6/30.
 */
public class InsertSort5 {

    public static void main(String[] args) {
        int[] arr = {3, 5, 2, 1, 9, 6, 8, 7};

        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            int tmp = arr[i];
            for (; j >= 0 && tmp < arr[j]; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = tmp;
        }

        for (int a : arr) {
            System.out.print(a + "\t");
        }

    }

}
