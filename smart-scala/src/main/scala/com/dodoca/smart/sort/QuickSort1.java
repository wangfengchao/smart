package com.dodoca.smart.sort;

import java.util.Arrays;

/**
 * Created by fengchao.wang on 2016/11/16
 **/
public class QuickSort1 {

    public static void main(String[] args) {
        int[] arr = { 50, 10, 90, 30, 70, 40, 80, 60, 20 };
        sort(arr, 0, arr.length - 1);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    public static void sort(int[] arr, int lo, int hi) {
         if (lo < hi) {
             int index = partition(arr, lo, hi);
             sort(arr, lo, index - 1);
             sort(arr, index + 1, hi);
         }
    }

    public static int partition(int[] arr, int lo, int hi) {
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
