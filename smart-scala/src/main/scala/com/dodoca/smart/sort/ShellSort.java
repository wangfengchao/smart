package com.dodoca.smart.sort;

/**
 * @author fengchao.wang
 * @create 2016-10-31 9:17
 **/
public class ShellSort {

    public static void main(String[] args) {
        int[] eles = {2, 35, 23, 10, 7, 9, 30, 50, 66};
        int a = eles.length;
        int temp = 0;
        while (true) {
            a = a / 2;
            for (int x = 0; x < a; x++) {
                for (int i = x + a; i < eles.length; i += a) {
                    int j = i - a;
                    temp = eles[i];
                    for (; j >= 0 && temp <eles[j]; j -= a) {
                        eles[j + a] = eles[j];
                    }
                    eles[j + a] = temp;
                }
            }

            if (a == 1) break;
        }

        for (int i : eles) {
            System.out.print(i +"\t");
        }

    }
}
