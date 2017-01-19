package com.dodoca.smart.sort;

/**
 * Created by  fc.w on 2017/1/16.
 */
public class QuickSort3 {

    public static void main(String[] args) {
        int [] arr = {3, 4, 8, 1, 2, 6, 7, 9, 5};
        for (int i = 0; i < arr.length; i++) {
            quickSort(arr, i, arr.length - 1);
        }

        for (int i : arr) {
            System.out.print(i + "\t");
        }
    }

    public static void quickSort(int[] data, int low, int hight) {
        if (low < hight) {
            int middle = getMiddle(data, low, hight);
            quickSort(data, 0, middle);
            quickSort(data, middle + 1, hight);
        }
    }

    private static int getMiddle(int[] data, int low, int hight) {
        int tmp = data[low];
        while (low < hight) {
            while (low < hight && data[hight] >= tmp) hight--;
            data[low] = data[hight];
            while (low < hight && data[low] <= tmp) low++;
            data[hight] = data[low];
        }

        data[low] = tmp;
        return hight;
    }

}
