package com.smart.algorithm.sort;


import java.util.Arrays;

/**
 * Created by fc.w on 2017/10/28.
 */
public class HeapSort4 {

    public static void main(String[] args) {
        int []arr = {9,8,7,6,5,4,3,2,1};
        heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void heapSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            // 从最后一个非叶子节点从左到右，从下到上调整结构
            createHeap(arr, arr.length - i - 1);
            // 将堆顶元素与末尾元素交换位置
            swap(arr, 0, arr.length - i - 1);
        }

    }

    public static void createHeap(int[] arr, int lastIndex) {
        for (int i = (arr.length / 2) - 1; i >= 0; i--) {
            // 非叶子节点下标
            int temp = i;
            while ((temp * 2 + 1) <= lastIndex) {
                int bigIndex = temp * 2 + 1;
                if (bigIndex < lastIndex) {
                    if (arr[bigIndex] < arr[bigIndex + 1]) {
                        bigIndex++;
                    }
                }

                if (arr[bigIndex] > arr[temp]) {
                    // 交换非叶子节点元素的位置
                    swap(arr, temp, bigIndex);
                    // 将元素下标赋值，便于之后的非叶子节点调整树结构
                    temp = bigIndex;
                } else {
                    break;
                }
            }

        }
    }

    public static void swap(int[] arr, int k, int bigIndex) {
        int temp = arr[k];
        arr[k] = arr[bigIndex];
        arr[bigIndex] = temp;
    }

}
