package com.smart.algorithm.sort;

/**
 * Created by  fc.w on 2017/1/11.
 */
public class QuickSort2 {

    public static void main(String[] args) {
        int [] arr = {2, 9, 7, 4, 5, 0, 3, 6, 1, 8};
        sort(arr, 0, arr.length - 1);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    public static void sort(int[] arr, int lo, int hi) {
        if (lo < hi) {
            int k = partition(arr, lo, hi);
            sort(arr, k + 1, hi);
            sort(arr, lo, k - 1);
        }
    }

    public static int partition(int[] arr, int lo , int hi) {
        int k = arr[lo];
        while (lo < hi) {
            while (arr[hi] > k && lo < hi) hi--;
            arr[lo] = arr[hi];
            while (arr[lo] < k && lo < hi) lo++;
            arr[hi] = arr[lo];
        }
        arr[hi] = k;
        return hi;
    }

}
