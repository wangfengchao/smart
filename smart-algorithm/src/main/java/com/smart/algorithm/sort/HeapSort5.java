package com.smart.algorithm.sort;

import java.util.Arrays;

/**
 * Created by fc.w on 2017/10/29.
 */
public class HeapSort5 {

    public static void main(String[] args) {
        int []arr = {4,2,7,6,1,9,3,8,5};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            // 从最后一个非叶子节点从左到右，从下到上调整结构
            createHeap(arr, arr.length - i - 1);
            // 将堆顶元素与末尾元素交换位置
            swap(arr, 0, arr.length - i - 1);
        }
    }

    /**
     * 构建堆
     * @param arr
     * @param lastIndex
     */
    public static void createHeap(int[] arr, int lastIndex) {
        for (int i = (arr.length / 2) - 1; i >= 0; i--) {
            // 非叶子节点下标
            int t = i;
            while ((t * 2 + 1) <= lastIndex) {
                int bigIndex = t * 2 + 1;
                if (bigIndex < lastIndex) {
                    if (arr[bigIndex] < arr[bigIndex + 1]) {
                        bigIndex++;
                    }
                }

                if (arr[bigIndex] > arr[t]) {
                    // 交换非叶子节点元素的位置
                    swap(arr, bigIndex, t);
                    // 将元素下标赋值，便于之后的非叶子节点调整树结构
                    t = bigIndex;
                } else {
                    break;
                }

            }
        }
    }

    /**
     * 交换元素位置
     * @param arr
     * @param i
     * @param j
     */
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


}
