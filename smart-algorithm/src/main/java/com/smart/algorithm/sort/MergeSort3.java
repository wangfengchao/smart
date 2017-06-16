package com.smart.algorithm.sort;

/**
 * Created by fc.w on 2017/6/14.
 */
public class MergeSort3 {

    public static void main(String[] args) {
        int [] arr = {3, 4, 8, 1, 2, 6, 7, 9, 5};
        sort(arr, 0, arr.length - 1);

        for (int a : arr) {
            System.out.print(a + "\t");
        }
    }

    private static void sort(int[] arr, int left, int right) {
        if (left >= right)
            return ;
        int center = (left + right) / 2;
        sort(arr, left, center);
        sort(arr, center + 1, right);
        merge(arr, left, center, right);
    }

    private static void merge(int[] arr, int left, int center, int right) {
        int[] tmpArr = new int[arr.length];
        int mid = center + 1;
        int third = left;
        int tmpIndex = left;

        while (left <= center && mid <= right) {
            if (arr[left] <= arr[mid]) {
                tmpArr[third++] = arr[left++];
            } else {
                tmpArr[third++] = arr[mid++];
            }
        }

        while (mid <= right) {
            tmpArr[third++] = arr[mid++];
        }
        while (left <= center) {
            tmpArr[third++] = arr[left++];
        }

        while (tmpIndex <= right) {
            arr[tmpIndex] = tmpArr[tmpIndex++];
        }
    }


}
