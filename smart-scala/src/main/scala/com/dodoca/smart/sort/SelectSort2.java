package com.dodoca.smart.sort;

/**
 * Created by  fc.w on 2016/12/27.
 */
public class SelectSort2 {

    public static void main(String[] args) {
        int[] arr = {2, 35, 23, 10, 7, 9, 30, 50, 66};
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            int tmp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = tmp;
        }

        for (int i : arr) {
            System.out.print(i + "\t");
        }
    }
}
