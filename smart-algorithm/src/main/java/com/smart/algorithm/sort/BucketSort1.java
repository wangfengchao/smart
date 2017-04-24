package com.smart.algorithm.sort;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by  fc.w on 2017/2/6.
 */
public class BucketSort1 {

    public static void bucketSort(int[] arr) {
        // 计算最大值和最小值
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
            min = Math.min(min, arr[i]);
        }
        // 计算桶数量和初始化
        int bucketNum = (max - min) / arr.length + 1;
        ArrayList<ArrayList<Integer>> bucketList = new ArrayList<ArrayList<Integer>>(bucketNum);
        for (int i = 0; i < bucketNum; i++) {
            bucketList.add(new ArrayList<Integer>());
        }
        // 把每个元素放入对应的桶中
        for (int i = 0; i < arr.length; i++) {
            int num = (arr[i] - min) / arr.length;
            bucketList.get(num).add(arr[i]);
        }
        // 对每个桶排序
        for (int i = 0; i < bucketList.size(); i++) {
            Collections.sort(bucketList.get(i));
        }

        System.out.println(bucketList.toString());
    }

    public static void main(String[] args) {
        int[] data = new int[] { 5, 3, 6, 42, 1, 99, 34, 8, 7 ,5, 3, 6, 2, 61, 49, 34, 18, 17, 15, 3, 46, 32, 1, 9, 4, 8, 27 ,5, 23, 6, 22, 1, 9, 4, 8, 7};
        bucketSort(data);
    }
}
