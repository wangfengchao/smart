package com.smart.algorithm.sort;

/**
 * @author fengchao.wang
 * @create 2016-10-31 9:10
 **/
public class InsertSort {

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

        for (int ele : eles) {
            System.out.println(ele);
        }
    }

}
