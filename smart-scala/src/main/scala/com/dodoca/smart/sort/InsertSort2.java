package com.dodoca.smart.sort;

/**
 * Created by  fc.w on 2016/12/27.
 */
public class InsertSort2 {

    public static void main(String[] args) {
        int[] arr = { 50, 10, 90, 30, 70, 40, 80, 60, 20 };
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            int tmp = arr[i];
            for (; j >= 0 && arr[j] > tmp; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = tmp;
        }

        for (int i : arr) {
            System.out.print(i + "\t");
        }
    }
}
