package com.smart.algorithm.sort;

import java.util.Arrays;

/**
 * Created by fc.w on 2017/10/29.
 */
public class InsertSort6 {

    public static void main(String[] args) {
        int [] arr = {3, 4, 8, 1, 2, 6, 7, 9, 5};
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            int temp = arr[i];
            for (; j >= 0 && arr[j] < temp; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = temp;
        }

        System.out.println(Arrays.toString(arr));

    }

}
