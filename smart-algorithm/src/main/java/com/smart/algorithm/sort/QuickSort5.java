package com.smart.algorithm.sort;

import java.util.Arrays;

/**
 * 三数取中
 * Created by fc.w on 2017/10/29.
 */
public class QuickSort5 {

    public static void main(String[] args) {
        int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        quickSort(arr, 0, arr.length - 1);
        System.out.println("排序结果：" + Arrays.toString(arr));
    }

    public static void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            // 获取枢纽值，并将其放入待排序的末尾
            dealPivot(arr, left, right);
            // 枢纽值被放到带排序的末尾
            int pivot = right - 1;
            int i = left;
            int j = right - 1;
            while (true) {
                while (arr[i] < arr[pivot]) i++;
                while (j > left && arr[j] > arr[pivot]) j--;
                // 双向扫描得到左序列大于枢纽值的数和有序列小于枢纽值的数后，两个值交换。
                if (i < j) {
                    swap(arr, i, j);
                } else {
                    break; // i j 发生碰撞则退出
                }
            }
            // 双向扫描得到大于枢纽值的值后，与枢纽值交换
            if (i < right) {
                swap(arr, i, right - 1);
            }
            // 左序列递归
            quickSort(arr, left, i - 1);
            // 右序列递归
            quickSort(arr, i + 1, right);
        }
    }

    /**
     * 处理枢纽值
     * @param arr
     * @param left
     * @param right
     */
    public static void dealPivot(int[] arr, int left, int right) {
        int mid = (left + right) / 2;
        if (arr[left] > arr[mid]) swap(arr, left, mid);
        if (arr[mid] > arr[right]) swap(arr, mid, right);
        if (arr[left] > arr[right]) swap(arr, left, right);
    }

    /**
     * 元素交换位置
     * @param arr
     * @param left
     * @param right
     */
    public static void swap(int[] arr, int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }


}
