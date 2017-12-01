package com.smart.algorithm.sort;

import java.util.Arrays;

/**
 * Created by fc.w on 2017/11/24.
 */
public class MergeTest {

    public static void main(String[] args) {
        int[] arr = {2, 6, 3, 1, 8, 0, 7, 9};
        sort(arr, 0, arr.length - 1);
        System.out.println("排序结果：" + Arrays.toString(arr));
    }

    private static void sort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            sort(arr, left, mid);
            sort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int[] tempArr = new int[arr.length];
        int i = left; // 左指针
        int j = mid + 1; // 右指针
        int tmpIndex = 0;
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                tempArr[tmpIndex++] = arr[i++];
            } else {
                tempArr[tmpIndex++] = arr[j++];
            }
        }

        while (i <= mid) {
            tempArr[tmpIndex++] = arr[i++];
        }
        while (j <= right) {
            tempArr[tmpIndex++] = arr[j++];
        }

        int t = 0;
        while (left <= right) {
            arr[left++] = tempArr[t++];
        }

    }

}
