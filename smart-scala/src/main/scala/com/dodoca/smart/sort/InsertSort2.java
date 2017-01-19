package com.dodoca.smart.sort;

/**
 * Created by  fc.w on 2017/1/6.
 */
public class InsertSort2 {

    public static void main(String[] args) {
        int [] arr = {2, 9, 7, 4, 5, 0, 3, 6, 1, 8};
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            int tmp = arr[i];
            for (; j >= 0 && arr[j] > tmp; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = tmp;
        }

        for (int ele : arr) {
            System.out.print(ele + "\t");
        }
    }
}
