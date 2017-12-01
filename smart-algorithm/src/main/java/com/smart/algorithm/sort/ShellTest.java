package com.smart.algorithm.sort;

import java.util.Arrays;

/**
 * Created by fc.w on 2017/11/23.
 */
public class ShellTest {

    public static void main(String[] args) {
        int[] arr = {2, 6, 9, 3, 1, 0, 4, 7, 8};
        sort(arr);
        System.out.println("排序结果：" + Arrays.toString(arr));
    }

    private static void sort(int[] arr) {
        for (int gap = (arr.length) / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                while (j - gap >= 0 && arr[j] < arr[j - gap]) {
                    swap(arr, j, j-gap);
                    j -= gap;
                }
            }
        }
    }

    /**
     * 交换位置
     * @param arr
     * @param l
     * @param r
     */
    private static void swap(int[] arr, int l, int r) {
        int temp = arr[l];
        arr[l] = arr[r];
        arr[r] = temp;
    }

}
