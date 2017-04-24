package com.smart.algorithm.sort;

/**
 * Created by Administrator on 2016/11/24.
 */
public class InsertSort1 {
    public static void main(String[] args) {
        int[] eles = {2, 35, 23, 10, 7, 9, 30, 50, 66};
        for (int i = 1; i < eles.length; i++) {
            int j = i - 1;
            int tmp = eles[i];
             for (; j >= 0 && eles[j] > tmp; j--) {
                 eles[j + 1] = eles[j];
             }
             eles[j + 1] = tmp;
        }


        for (int i : eles) {
            System.out.println(i + "\t");
        }
    }
}
