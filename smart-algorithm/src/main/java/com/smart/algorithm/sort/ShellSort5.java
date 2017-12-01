package com.smart.algorithm.sort;

import java.util.Arrays;

/**
 * Created by fc.w on 2017/10/29.
 */
public class ShellSort5 {

    public static void main(String[] args) {
        int[] arr = {1, 4, 2, 9, 5, 8, 7, 6};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        int mid = arr.length - 1 / 2;
        while (mid > 0) {
            for (int i = 0; i < mid; i++) {
                for (int j = i + mid; j < arr.length; j += mid) {
                    int k = j - mid;
                    int tmp = arr[j];
                    for (; k >= 0 && arr[k] > tmp; k -= mid) {
                        arr[k+mid] = arr[k];
                    }
                    arr[k + mid] = tmp;
                }
            }
            mid /= 2;
        }


    }

}
