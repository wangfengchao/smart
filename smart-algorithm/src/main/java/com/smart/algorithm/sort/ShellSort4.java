package com.smart.algorithm.sort;

/**
 * Created by fc.w on 2017/6/28.
 */
public class ShellSort4 {

    public static void main(String[] args) {
        int[] arr = {1, 4, 2, 9, 5, 8, 7, 6};

        int mid = arr.length - 1 / 2;
        while (mid > 0) {
            for (int i = 0; i < mid; i++) {
                for (int k = i + mid; k < arr.length; k += mid) {
                    int tmp = arr[k];
                    int w = k - mid;
                    for (;w >= 0 && arr[w] > tmp; w -= mid) {
                        arr[w + mid] = arr[w];
                    }
                    arr[w + mid] = tmp;
                }
            }
            mid /= 2;
        }

        for (int a : arr) {
            System.out.print(a + "\t");
        }

    }

}
