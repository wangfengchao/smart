package com.dodoca.smart.sort;

/**
 * Created by  fc.w on 2017/1/16.
 */
public class BubbleSort2 {

    public static void main(String[] args) {
        int [] arr = {3, 4, 8, 1, 2, 6, 7, 9, 5};
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] < arr[j + 1]) {
                    int tmp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = tmp;
                }
            }
        }

        for (int i : arr) {
            System.out.print(i + "\t");
        }
    }
}
