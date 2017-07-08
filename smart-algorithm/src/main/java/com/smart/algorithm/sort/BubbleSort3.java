package com.smart.algorithm.sort;

/**
 * Created by fc.w on 2017/6/30.
 */
public class BubbleSort3 {

    public static void main(String[] args) {
        int[] arr = {3, 5, 2, 1, 9, 6, 8, 7};

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }

        for (int a : arr) {
            System.out.print(a + "\t");
        }
    }

}
