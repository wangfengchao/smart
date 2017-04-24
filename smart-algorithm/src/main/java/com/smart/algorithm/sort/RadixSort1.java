package com.smart.algorithm.sort;

import java.util.Arrays;

/**
 * Created by  fc.w on 2017/2/7.
 */
public class RadixSort1 {

    public static void main(String[] args) {
        int[] array = {3,2,3,2,5,333,45566,2345678,78,990,12,432,56};
        radixSort(array,10,7);
        for (int i = 0; i < array.length; i++) {
            System.out.print("  " + array[i]);
        }
    }

    /**
     *
     * @param array 待排序数组
     * @param radix 基数
     * @param distance
     */
    private static void radixSort(int[] array, int radix, int distance) {
        int length = array.length;
        // 用于暂存元素
        int[] temp = new int[length];
        // 用于计数排序
        int[] count = new int[radix];
        int divide = 1;

        for (int i = 0; i < distance; i++) {
            System.arraycopy(array, 0, temp, 0, length);
            Arrays.fill(count, 0);

            for (int j = 0; j < length; j++) {
                int tempKey = (temp[j] / divide) % radix;
                count[tempKey]++;
            }

            for (int j = 1; j < radix; j++) {
                count[j] = count[j] + count[j - 1];
            }

            for (int j = length - 1; j >= 0; j--) {
                int tempKey = (temp[j] / divide) % radix;
                count[tempKey]--;
                array[count[tempKey]] = temp[j];
            }
            divide = divide * radix;
        }
    }

}
