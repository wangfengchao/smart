package com.smart.algorithm.sort;

/**
 * Created by  fc.w on 2017/1/10.
 */
public class HeapSort2 {

    public static void main(String[] args) {
        int [] arr = {2, 9, 7, 4, 5, 0, 3, 6, 1, 8};
        heapSort(arr);
        print(arr);
    }

    public static void heapSort(int[] data) {
        for (int i = 0; i < data.length; i++) {
            createMaxdHeap(data, data.length - 1 - i);
            swap(data, 0, data.length - 1 - i);
        }
    }

    public static void swap(int[] data, int k, int bigIndex) {
        if (k == bigIndex) return;
        int temp = data[k];
        data[k] = data[bigIndex];
        data[bigIndex] = temp;
    }

    public static void createMaxdHeap(int[] data, int lastIndex) {
        for (int i = (lastIndex - 1) / 2; i >= 0; i--) {
            int k = i;
            while ((k * 2 + 1) <= lastIndex) {
                int bigIndex = k * 2 + 1;
                if (bigIndex < lastIndex) {
                    if (data[bigIndex] < data[bigIndex + 1]) {
                        bigIndex++;
                    }
                }

                if (data[bigIndex] > data[k]) {
                    swap(data, k, bigIndex);
                    k = bigIndex;
                } else {
                    break;
                }

            }
        }
    }

    public static void print(int[] data) {
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + "\t");
        }
        System.out.println();
    }

}
