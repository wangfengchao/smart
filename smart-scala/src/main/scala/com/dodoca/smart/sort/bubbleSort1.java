package com.dodoca.smart.sort;

/**
 * Created by Administrator on 2016/11/25.
 */
public class bubbleSort1 {
    public static void main(String[] args) {
        int[] arr = { 50, 10, 90, 30, 70, 40, 80, 60, 20 };
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j +1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }

        for (int a : arr) {
            System.out.println(a +"\t");
        }
    }
}
