package com.dodoca.smart.sort;

/**
 * Created by Administrator on 2016/11/24.
 */
public class ShellSort1 {
    public static void main(String[] args) {
        int[] eles = {2, 35, 23, 10, 7, 9, 30, 50, 66};
        int d = eles.length / 2;
        while (d > 1) {
            for (int i = d; i < eles.length; i++) {
                int tmp = eles[i];
                int j = i - d;
                for (; j >= 0 && eles[j] > tmp; j = j - d) {
                    eles[j + d] = eles[j];
                }
                eles[j + d] = tmp;
            }

            d = d / 2;
        }

        for (int ele : eles) {
            System.out.println(ele + "\t");
        }
    }

}
