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
            int middle = getMiddle(arr, left, right);
            quickSort(arr, 0, middle);
            quickSort(arr, middle + 1, right);
        }
    }

    private static int getMiddle(int[] arr, int left, int right) {
        int tmp = arr[left];
        while (left < right) {
            while (left < right && arr[right] >= tmp) right--;
            arr[left] = arr[right];
            while (left < right && arr[left] <= tmp) left++;
            arr[right] = arr[left];
        }

        arr[left] = tmp;
        return right;
    }



}
