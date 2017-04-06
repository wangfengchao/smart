package com.smart.algorithm.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  fc.w on 2017/1/17.
 */
public class RadixSort {

    public static void main(String[] args) {
        int [] arr = {356, 36, 8, 100, 2, 672, 7, 9, 9700};
        radixSort(arr);
        for (int i : arr) {
            System.out.print(i + "\t");
        }
    }


    public static void radixSort(int[] data) {
        // 1. 选择最大元素，确定排序次数
        int max = data[0];
        for (int i = 0; i < data.length; i++) {
            if (max < data[i]) {
                max = data[i];
            }
        }

        // 2. 判断最大元素位数
        int times = 0;
        while (max > 0) {
            max /= 10;
            times++;
        }

        // 3. 建立10个队列
        List<ArrayList> queue = new ArrayList<ArrayList>();
        for (int i = 0; i < 10; i++) {
            ArrayList<Integer> queue1 = new ArrayList<Integer>();
            queue.add(queue1);
        }

        // 4. 进行times次分配和收集
        for (int i = 0; i < times; i++) {
            for (int j = 0; j < data.length; j++) {
                int x = (int) (data[j] % Math.pow(10, i + 1) / Math.pow(10, i));
                ArrayList<Integer> queue2 = queue.get(x);
                queue2.add(data[j]);
                queue.set(x, queue2);
            }

            // 5. 收集队列元素
            int count = 0;
            for (int k = 0; k < 10; k++) {
                while (queue.get(k).size() > 0) {
                    ArrayList<Integer> q = queue.get(k);
                    data[count] = q.get(0);
                    q.remove(0);
                    count++;
                }
            }
        }

    }
}
