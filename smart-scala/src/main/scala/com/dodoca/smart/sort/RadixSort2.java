package com.dodoca.smart.sort;

import java.util.Arrays;

/**
 * Created by  fc.w on 2017/2/8.
 */
public class RadixSort2 {

    public static void main(String[] args) {
        int[] array = {3,2,3,2,5,333,45566,2345678,78,990,12,432,56};
        radixSort(array, 10, 7);
        print(array);
    }

    private static void radixSort(int[] data, int radix, int distance) {
        int length = data.length;
        int [] tmp = new int[length];
        int [] bucket = new int[radix];
        int rate = 1;

        for (int i = 0; i < distance; i++) {
            // 填充bucket数组
            Arrays.fill(bucket, 0);
            // 带排序数据copy到临时数组
            System.arraycopy(data, 0, tmp, 0, length);

            for (int j = 0; j < length; j++) {
                int subKey = (tmp[j] / rate) % radix;
                bucket[subKey]++;
            }

            for (int j = 1; j < radix; j++) {
                bucket[j] = bucket[j] + bucket[j - 1];
            }

            // 按子关键字指定的数据进行排序
            for (int m = data.length - 1; m >= 0; m--) {
                int subKey = (tmp[m] / rate) % radix;
                data[--bucket[subKey]] = tmp[m];
            }

            rate *= radix;
        }

    }

    public static void print(int[] data) {
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + "\t");
        }
        System.out.println();
    }
}
