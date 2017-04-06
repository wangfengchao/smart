package com.smart.algorithm.sort;

/**
 * Created by  fc.w on 2017/1/17.
 */
public class MergeSort2 {

    public static void main(String[] args) {
//        int [] arr = {3, 4, 8, 1, 2, 6, 7, 9, 5};
//        sort(arr, 0, arr.length - 1);
//        for (int i : arr) {
//            System.out.print(i + "\t");
//        }

        System.out.println(126 % Math.pow(10, 1) / Math.pow(10, 0));
    }

    public static void sort(int[] data, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            sort(data, left, center);
            sort(data, center + 1, right);
            merge(data, left, center, right);
        }
    }

    public static void merge(int[] data, int left, int middle, int right) {
        int[] tmpData = new int[data.length];
        int rmid = middle + 1;
        int tmpIndex = left;
        int tmpLeft = left;
        while (left <= middle && rmid <= right) {
            if (data[left] >= data[rmid]) {
                tmpData[tmpIndex++] = data[rmid++];
            } else {
                tmpData[tmpIndex++] = data[left++];
            }
        }

        while (left <= middle) {
            tmpData[tmpIndex++] = data[left++];
        }
        while (rmid <= right) {
            tmpData[tmpIndex++] = data[rmid++];
        }

        while (tmpLeft <= right) {
            data[tmpLeft] = tmpData[tmpLeft++];
        }
    }
}
