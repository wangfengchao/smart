package com.smart.algorithm.sort;

/**
 * Created by Administrator on 2016/11/25.
 */
public class SelectSort1 {

    public static void main(String[] args) {
        int[] eles = {2, 35, 23, 10, 7, 9, 30, 50, 66};
        for (int i = 0; i < eles.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < eles.length; j++) {
                if (eles[minIndex] > eles[j]) {
                    minIndex = j;
                }
            }

            int tmp = eles[i];
            eles[i] = eles[minIndex];
            eles[minIndex] = tmp;
        }

        for (int ele : eles) {
            System.out.println(ele + "\t");
        }
    }
}
