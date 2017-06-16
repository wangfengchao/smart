package com.smart.algorithm.sort;

/**
 * Created by fc.w on 2017/6/13.
 */
public class QuickSort4 {

    public static void main(String[] args) {
        int [] arr = {3, 4, 8, 1, 2, 6, 7, 9, 5};

        quickSort(arr, 0, arr.length - 1);

        for (int a : arr) {
            System.out.print(a + "\t");
        }
    }

    private static void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int key = arr[left];
            int low = left;
            int hight = right;
            while (low < hight) {
                while (low < hight && key < arr[hight]) {
                    hight--;
                }
                arr[low] = arr[hight];
                while (low < hight && key > arr[low]) {
                    low++;
                }
                arr[hight] = arr[low];
            }

            arr[low] = key;
            quickSort(arr, left, low - 1);
            quickSort(arr, low + 1, right);
        }
    }

}
