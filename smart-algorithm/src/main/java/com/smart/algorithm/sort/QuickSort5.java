package com.smart.algorithm.sort;

/**
 * Created by fc.w on 2017/6/13.
 */
public class QuickSort5 {

    public static void main(String[] args) {
        int [] arr = {3, 4, 8, 1, 2, 6, 7, 9, 5};
        quickSort(arr, 0, arr.length - 1);

        for (int a : arr) {
            System.out.print(a + "\t");
        }

    }

    private static void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int tmp = arr[left];
            int low = left;
            int high = right;
            while (low < high) {
                while (low < high && tmp < arr[high]) {
                    high--;
                }
                arr[low] = arr[high];
                while (low < high && tmp > arr[low]) {
                    low++;
                }
                arr[high] = arr[low];
            }

            arr[low] = tmp;
            quickSort(arr, left, low - 1);
            quickSort(arr, low + 1, right);
        }
    }

}
