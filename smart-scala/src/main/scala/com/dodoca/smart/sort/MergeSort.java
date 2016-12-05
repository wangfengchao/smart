package com.dodoca.smart.sort;

/**
 * Created by fengchao.wang on 2016/11/14
 **/
public class MergeSort {

    public static void main(String[] args) {
        int[] data = new int[] { 5, 3, 6, 2, 1, 9, 4, 8, 7 };
        System.out.println("排序前：");
        print(data);
        mergeSort(data);
        System.out.println("排序后：");
        print(data);
    }

    public static void mergeSort(int[] data) {
        sort(data, 0, data.length - 1);
    }

    public static void sort(int[] data, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            sort(data, left, center);
            sort(data, center + 1, right);
            merge(data, left, center, right);
            print(data);
        }
    }

    public static void merge(int[] data, int left, int center, int right) {
        int[] tempArr = new int[data.length];
        int rightMid = center + 1;
        int third = left;
        int tmp = left;
        while (left <= center && rightMid <= right) {
            if (data[left] >= data[rightMid])
                tempArr[third++] = data[rightMid++];
            else tempArr[third++] = data[left++];
        }

        while (rightMid <= right) {
            tempArr[third++] = data[rightMid++];
        }
        while (left <= center) {
            tempArr[third++] = data[left++];
        }

        while (tmp <= right) {
            data[tmp] = tempArr[tmp++];
        }
    }

    public static void print(int[] data) {
        for (int i : data) {
            System.out.print(i +"\t");
        }
        System.out.println();
    }
}
