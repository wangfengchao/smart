package com.smart.algorithm.sort;

import java.util.Arrays;

/**
 * Created by fc.w on 2017/10/28.
 */
public class MergeSort4 {
    public static void main(String[] args) {
        int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        sort(arr, 0, arr.length - 1);
        System.out.println("排序结果：" + Arrays.toString(arr));
    }

    public static void sort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            sort(arr, left, mid);
            sort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    public static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[arr.length];
        int i = left; // 左序列
        int j = mid + 1; // 右序列
        int t = 0; // 临时数组下标
        while(i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[t++] = arr[i++];
            } else {
                temp[t++] = arr[j++];
            }
        }

        while (i <= mid) {
            temp[t++] = arr[i++];
        }
        while (j <= right) {
            temp[t++] = arr[j++];
        }

        t = 0;
        while (left <= right) {
            arr[left++] = temp[t++];
        }

    }

}