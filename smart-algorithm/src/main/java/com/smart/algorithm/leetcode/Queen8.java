package com.smart.algorithm.leetcode;

/**
 *
 * Created by fc.w on 2017/5/17.
 */
public class Queen8 {

    static int num = 0; // 累计方案数
    static final int MAXQUEEN = 8; //
    static int[] cols = new int[MAXQUEEN];

    public Queen8() {
        getArrangement(0);
        System.out.print("\n");
        System.out.println(MAXQUEEN+"皇后问题有"+num+"种摆放方法。");
        System.out.println();
    }

    public void getArrangement(int n) {
        boolean[] rows = new boolean[MAXQUEEN];
        for (int i = 0; i < n; i++) {
            rows[cols[i]] = true;
            int d = n - i;
            if (cols[i] - d >= 0) rows[cols[i] - d] = true;
            if (cols[i] + d <= MAXQUEEN - 1) rows[cols[i] + d] = true;
        }

        for (int i = 0; i < MAXQUEEN; i++) {
            if (rows[i]) continue;
            cols[n] = i;
            if (n < MAXQUEEN - 1){
                getArrangement(n + 1);
            } else {
                num++;
                printChessBoard();
            }
        }
    }

    public void printChessBoard() {
        System.out.print("第"+num+"种走法 \n");
        for(int i=0;i<MAXQUEEN;i++) {
            for (int j = 0; j < MAXQUEEN; j++) {
                if (i == cols[j]) {
                    System.out.print("0 ");
                } else {
                    System.out.print("+ ");
                }
            }
            System.out.print("\n");
        }
    }

    public static void main(String args[]){
        Queen8 queen = new Queen8();
    }


}
