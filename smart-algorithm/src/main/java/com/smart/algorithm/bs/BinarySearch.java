package com.smart.algorithm.bs;

/**
 * 二分查找算法
 1.二分查找又称折半查找，它是一种效率较高的查找方法。

 2.二分查找要求：（1）必须采用顺序存储结构 （2）.必须按关键字大小有序排列

 3.原理：将数组分为三部分，依次是中值（所谓的中值就是数组中间位置的那个值）前，中值，中值后；
 将要查找的值和数组的中值进行比较，若小于中值则在中值前 面找，若大于中值则在中值后面找，等于中值时直接返回。
 然后依次是一个递归过程，将前半部分或者后半部分继续分解为三部分。

 4.实现：二分查找的实现用递归和循环两种方式
 * Created by fc.w on 2018/4/12.
 */
public class BinarySearch {

    /**
     * 二分查找 循环
     * @param arr
     * @param x
     * @return
     */
    public static int binarySearch(int[] arr, int x) {
        int lower = 0;
        int high = arr.length - 1;
        while (lower < high) {
            int mid = (lower + high) / 2;
            if (x == arr[mid]) {
                return mid;
            } else if (x < arr[mid]) {
                high = mid - 1;
            } else {
                lower = mid + 1;
            }
        }
        return -1;
    }

    /**
     *
     * @param arr
     * @param data
     * @param beginIndex
     * @param endIndex
     * @return
     */
    public static int binarySearch(int[] arr, int data, int beginIndex, int endIndex) {
        int mid = (beginIndex + endIndex) / 2;
        if (data < arr[beginIndex] || data > arr[endIndex] || beginIndex > endIndex) {
            return -1;
        }
        if (data < arr[mid]) {
            return binarySearch(arr, data, beginIndex, mid - 1);
        } else if (data > arr[mid]) {
            return binarySearch(arr, data, mid + 1, endIndex);
        } else {
            return mid;
        }
    }

    public static void main(String[] args) {
        int[] arr = { 6, 12, 33, 87, 90, 97, 108, 561 };
        System.out.println("循环查找：" + (binarySearch(arr, 87) + 1));
        System.out.println("递归查找: " + (binarySearch(arr, 87, 0, arr.length-1) + 1));
    }

}
