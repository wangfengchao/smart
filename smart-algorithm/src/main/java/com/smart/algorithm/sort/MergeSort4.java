package com.smart.algorithm.sort;

import java.util.Arrays;

/**
 * Created by fc.w on 2017/10/28.
 */
public class MergeSort4 {

    public static void main(String[] args) {
        int []arr = {9,8,7,6,5,4,3,2,1};
        sort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
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
        int[] temp = new int[arr.length]; // 临时数组
        int i = left; // 左序列指针
        int j = mid + 1; // 有序列指针
        int t = 0; // 临时数组当前下标

        while (i <= mid && j <=right) {
            if (arr[i] > arr[j]) {
                temp[t++] = arr[j++];
            } else {
                temp[t++] = arr[i++];
            }
        }

        // 将左序列剩余的数据拷贝到临时数组中
        while (i <= mid) {
            temp[t++] = arr[i++];
        }
        // 将有序列剩余的数据拷贝到临时数组中
        while (j <= right) {
            temp[t++] = arr[j++];
        }

        // 把临时数组中的数据拷贝到原数组中
        t = 0;
        while (left <= right) {
            arr[left++] = temp[t++];
        }

    }

}
