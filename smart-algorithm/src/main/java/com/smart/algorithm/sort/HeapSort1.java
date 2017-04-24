package com.smart.algorithm.sort;

/**
 * Created by fengchao.wang on 2016/11/03
 **/
public class HeapSort1 {
    public static void main(String[] args) {
        int[] array = { 96, 8, 13, 6, 5, 4, 27, 2, 1, 0, -1, -20, -3 };
        heapSort(array);
        print(array);
    }

    public static void heapSort(int[] data) {
        for (int i = 0; i < data.length; i++) {
            createMaxHeap(data, data.length -1 - i);
            swap(data, 0, data.length - 1 - i);
        }
    }

    public static void createMaxHeap(int[] data, int lastIndex) {
        for (int i = (lastIndex - 1)/2; i >= 0; i--) {
            int k = i;
            while ((2*k+1) <= lastIndex) {
                int bigIndex = (2 * k + 1);
                if (bigIndex < lastIndex) {
                    if (data[bigIndex] < data[bigIndex + 1]) {
                        bigIndex++;
                    }
                }

                if (data[k] < data[bigIndex]) {
                    swap(data, k, bigIndex);
                    k = bigIndex;
                } else {
                    break;
                }

            }
        }
    }

    public static void swap(int[] data, int i, int j) {
        if (i == j) {
            return;
        }
        data[i] = data[i] + data[j];
        data[j] = data[i] - data[j];
        data[i] = data[i] - data[j];
    }

    public static void print(int[] data) {
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + "\t");
        }
        System.out.println();
    }
}
