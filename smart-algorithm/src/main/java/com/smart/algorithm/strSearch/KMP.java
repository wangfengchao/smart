package com.smart.algorithm.strSearch;

/**
 * Created by fc.w on 2017/9/6.
 */
public class KMP {

    private static void next(char[] input, int[] table) {
        int pos = 2;
        int cnd = 0;

        table[0] = -1;
        table[1] = 0;

        while(pos < input.length) {
            if(input[pos - 1] == input[cnd]) {
                table[pos] = cnd + 1;
                pos++;
                cnd++;
            } else if(cnd > 0) {
                cnd = table[cnd];
            } else {
                table[pos] = 0;
                pos++;
            }
        }
    }

    public static void main(String[] args) {
        ;
    }

}
