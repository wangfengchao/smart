package com.smart.algorithm.sort;

import java.util.Arrays;

/**
 * Created by fc.w on 2017/11/22.
 */
public class HeapTest {

    public static void main(String[] args) {
        int[] arr = {3, 9, 7, 4, 2, 1, 5, 8};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void sort(int[] arr) {
        for (int i = 0 ; i < arr.length; i++) {
            // 构建堆
            buildHeap(arr, arr.length - i - 1);
            // 堆顶元素与末尾元素交换位置
            swap(arr, 0, arr.length - i - 1);
        }
    }

    /**
     * 构建堆
     * @param arr
     * @param length
     */
    private static void buildHeap(int[] arr, int length) {
        // 从非叶节点的最后一个元素开始
        for (int i = (arr.length / 2) - 1; i >= 0; i--) {
            int tmpIndex = i;
            while ((tmpIndex * 2 + 1) <= length) {
                int bigIndex = (tmpIndex * 2) + 1;
                if (bigIndex < length) {
                    if (arr[bigIndex] < arr[bigIndex + 1]) {
                        bigIndex++;
                    }
                }

                if (arr[bigIndex] > arr[tmpIndex]) {
                    swap(arr, bigIndex, tmpIndex);
                    tmpIndex = bigIndex;
                } else {
                    break;
                }
            }
        }
    }


    /**
     * 元素交换位置
     * @param arr
     * @param left
     * @param right
     */
    private static void swap(int[] arr, int left, int right) {
        int tmp = arr[left];
        arr[left] = arr[right];
        arr[right] = tmp;
    }
}
