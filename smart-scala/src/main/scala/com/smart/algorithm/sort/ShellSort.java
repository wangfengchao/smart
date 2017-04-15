package com.smart.algorithm.sort;

/**
 * @author fengchao.wang
 * @create 2016-10-31 9:17
 **/
public class ShellSort {

    public static void main(String[] args) {
        int[] eles = {2, 35, 23, 10, 7, 9, 30, 50, 66};
        int h = eles.length / 2;
        while (h > 1) {
            for (int i = h; i < eles.length; i++) {
                int j = i - h;
                int tmp = eles[i];
                for (; j >= 0 && eles[j] > tmp; j = j - h) {
                    eles[j + h] = eles[j];
                }
                eles[j + h] = tmp;
            }
            h = h / 2;
        }

        for (int ele : eles) {
            System.out.println(ele);
        }
    }
}
