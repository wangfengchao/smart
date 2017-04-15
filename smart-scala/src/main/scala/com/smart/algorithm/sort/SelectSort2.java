package com.smart.algorithm.sort;

/**
 * Created by  fc.w on 2017/1/6.
 */
public class SelectSort2 {

    public static void main(String[] args) {
        int [] arr = {2, 9, 7, 4, 5, 0, 3, 6, 1, 8};
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[minIndex] > arr[j]) {
                    minIndex = j;
                }
            }

            int tmp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = tmp;
        }

        for (int a : arr) {
            System.out.print(a + "\t");
        }
    }
}
