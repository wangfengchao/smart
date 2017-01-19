package com.dodoca.smart.sort;

/**
 * Created by  fc.w on 2017/1/12.
 */
public class MergeSort1 {

    public static void main(String[] args) {
        int [] arr = {2, 9, 7, 4, 5, 0, 3, 6, 1, 8};
        mergeSort(arr);
        print(arr);
    }

    public static void mergeSort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    public static void sort(int[] data, int left, int right) {
        if (left >= right) return;
        int center = (left + right) / 2;
        sort(data, left, center);
        sort(data, center + 1, right);
        merge(data, left, center, right);
    }

    public static void merge(int[] data, int left, int center, int right) {
        int[] tmpArr = new int[data.length];
        int rmid = center + 1;
        int tmpIndex = left;
        int tmp = left;

        while (left <= center && rmid <= right) {
            if (data[left] >= data[rmid]) {
                tmpArr[tmpIndex++] = data[rmid++];
            } else {
                tmpArr[tmpIndex++] = data[left++];
            }
        }

        while (left <= center) {
            tmpArr[tmpIndex++] = data[left++];
        }
        while (rmid <= right) {
            tmpArr[tmpIndex++] = data[rmid++];
        }
        while (tmp <= right) {
            data[tmp] = tmpArr[tmp++];
        }

    }

    public static void print(int[] data) {
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + "\t");
        }
        System.out.println();
    }

}
