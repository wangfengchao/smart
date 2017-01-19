package com.dodoca.smart.sort;

/**
 * Created by  fc.w on 2017/1/16.
 */
public class InsertSort4 {

    public static void main(String[] args) {
        int[] arr = {3, 4, 8, 1, 2, 6, 7, 9, 5};
        for (int i = 1; i < arr.length; i++) {
            int tmp = arr[i];
            int j = i - 1;
            for (; j >= 0 && tmp < arr[j]; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = tmp;
        }

        for (int ele : arr) {
            System.out.print(ele + "\t");
        }
    }
}
