package com.smart.algorithm.sort;

/**
 * Created by fengchao.wang on 2016/11/16
 **/
public class HeapSortTest {
    public static void main(String[] args) {
        int[] array = { 96, 8, 13, 6, 5, 4, 27, 2, 1, 0, -1, -20, -3 };
        for (int i = 0; i < array.length; i++) {
            createMaxHeap(array, array.length - i - 1);
            swap(array, 0, array.length - 1 - i);
        }
        print(array);
    }

    public static void createMaxHeap(int[] array, int lastIndex) {
        for (int i = (lastIndex - 1) / 2; i >= 0; i--) {
            int k = i;
            while((2*k+1) <= lastIndex) {
                int bigIndex = 2 * k + 1;
                if (bigIndex < lastIndex) {
                    if (array[bigIndex] < array[bigIndex + 1]) bigIndex++;
                }
                if (array[k] < array[bigIndex]) {
                    swap(array, k, bigIndex);
                    k = bigIndex;
                } else {
                    break;
                }
            }
        }
    }

    public static  void  swap(int[] data, int k, int bigIndex) {
        if (k != bigIndex) {
            data[k] = data[k] + data[bigIndex];
            data[bigIndex] = data[k] - data[bigIndex];
            data[k] = data[k] - data[bigIndex];
        }
    }

    public static void print(int[] data) {
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + "\t");
        }
        System.out.println();
    }
}
