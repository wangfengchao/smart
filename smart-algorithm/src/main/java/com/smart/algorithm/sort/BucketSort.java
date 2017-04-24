package com.smart.algorithm.sort;

/**
 * Created by fengchao.wang on 2016/11/15
 **/
public class BucketSort {
    public static void main(String[] args) {
        int[] data = new int[] { 5, 3, 6, 2, 1, 9, 4, 8, 7 ,5, 3, 6, 2, 1, 9, 4, 8, 7, 5, 3, 6, 2, 1, 9, 4, 8, 7 ,5, 3, 6, 2, 1, 9, 4, 8, 7};
        bucketSort(data,10);
        print(data);
    }

    public static void bucketSort(int[] data, int max) {
        int[] tmp = new int[data.length];
        int[] buckets = new int[max];
        for (int i = 0; i < data.length; i++) {
            buckets[data[i]]++;
        }
        for (int i = 1; i < max ; i++) {
            buckets[i] = buckets[i] + buckets[i - 1];
        }
        System.arraycopy(data, 0, tmp, 0, data.length);
        for (int k = data.length - 1; k >= 0; k--) {
            data[--buckets[tmp[k]]] = tmp[k];
        }
    }

    public static void print(int[] data) {
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + "\t");
        }
        System.out.println();
    }
}
