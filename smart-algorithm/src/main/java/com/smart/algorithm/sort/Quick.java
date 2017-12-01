package com.smart.algorithm.sort;

import java.util.Arrays;

/**
 * 快速排序
 * Created by fc.w on 2017/11/21.
 */
public class Quick {

    public static void main(String[] args) {
        int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        quickSort(arr, 0, arr.length - 1);
        System.out.println("排序结果：" + Arrays.toString(arr));
    }

    /**
     * 排序
     * @param arr
     * @param left
     * @param right
     */
    private static void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            // 获取枢纽值，并将其放在当前待处理序列末尾
            dealPivot(arr, left, right);
            // 枢纽值被放在序列末尾
            int pivot = arr[right - 1];
            // 左指针
            int i = left;
            // 右指针
            int j = right - 1;
            while (true) {
                while (arr[++i] < arr[pivot]){}
                while (j > left && arr[--j] > arr[pivot]) {}
                if (i < j) {
                    swap(arr, i, j);
                } else {
                    break;
                }

            }

            if (i < right) {
                swap(arr, i, right - 1);
            }
            // 分治法
            quickSort(arr, left, i - 1);
            quickSort(arr, i + 1, right);
        }
    }

    /**
     * 处理枢纽值
     * @param arr
     * @param left
     * @param right
     */
    private static void dealPivot(int[] arr, int left, int right) {
        int mid = (left + right) / 2;
        if (arr[left] > arr[right]) {
            swap(arr, left, right);
        }
        if (arr[left] > arr[mid]) {
            swap(arr, left, mid);
        }
        if (arr[right] < arr[mid]) {
            swap(arr, right, mid);
        }
        // 枢纽值被放在序列末尾
        swap(arr, right - 1, mid);
    }

    /**
     * 数据交换
     * @param arr
     * @param l
     * @param r
     */
    private static void swap(int[] arr, int l, int r) {
        int tmp = arr[l];
        arr[l] = arr[r];
        arr[r] = tmp;
    }


}
