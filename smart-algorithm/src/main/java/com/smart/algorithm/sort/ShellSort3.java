package com.smart.algorithm.sort;

/**
 * Created by  fc.w on 2017/1/16.
 */
public class ShellSort3 {

    public static void main(String[] args) {
        int [] arr = {3, 4, 8, 1, 2, 6, 7, 9, 5};
        int d = (int)Math.ceil(arr.length / 2);
        while (d >= 1) {
            for (int i = 0; i < d; i++) {
                for (int x = i + d; x < arr.length; x += d) {
                    int j = x - d;
                    int tmp = arr[x];
                    for (; j >= 0 && tmp < arr[j]; j -= d) {
                        arr[j + d] = arr[j];
                    }
                    arr[j + d] = tmp;
                }
            }
            d /= 2;
        }

        for (int ele : arr) {
            System.out.print(ele + "\t");
        }

    }
}
