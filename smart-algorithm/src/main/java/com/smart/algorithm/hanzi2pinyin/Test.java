package com.smart.algorithm.hanzi2pinyin;

import java.io.UnsupportedEncodingException;

/**
 * Created by fc.w on 2017/9/5.
 */
public class Test {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String str1 = "上海";
        String str2 = "上海";
        byte[] byte1 = str1.getBytes();
        byte[] byte2 = str2.getBytes();

        System.out.println(str2.compareTo(str1));

        if (byte1.equals(byte2)) {
            System.out.println("equals");
        } else {
            System.out.println("no equals");
        }

    }

}
