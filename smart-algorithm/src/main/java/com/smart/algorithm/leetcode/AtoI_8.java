package com.smart.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 1. 前面的空格
 2. 除去前面的空格后，可以以“+、-、0”开头，需要做对应的处理
 3. 除了起始处可以出现前2种情况提到的非数字字符，其他地方一旦出现，则忽略该字符以及其后的字符
 4. 考虑边界，即是否超出Integer.MAX_VALUE，Integer.MIN_VALUE。下面的方案采用long作为临时存储，方便做边界的判断。
 但是还要考虑是否会超出long的最大值，所以笔者采用length长度做初步判断。
 * Created by Administrator on 2017/4/16.
 */
public class AtoI_8 {

    public static void main(String[] args) {
        System.out.println(myAtoi("-0012a42"));
    }

    public static int myAtoi(String str) {
        char[] charArr=str.toCharArray();
        Long result=0L;
        int startIndex=0;
        boolean flag=true;//正数
        int length=0;
        for(int i=0;i<charArr.length;i++){
            if(startIndex==i){
                if(charArr[i]==' '){
                    startIndex++;
                    continue;
                }
                if(charArr[i]=='+'||charArr[i]=='0'){
                    continue;
                }
                if(charArr[i]=='-'){
                    flag=false;
                    continue;
                }
            }
            if(charArr[i]>='0'&&charArr[i]<='9'){
                result=result*10+charArr[i]-'0';
                length++;
                if(length>10){
                    break;
                }
            }else{
                break;
            }
        }
        if(flag){
            if(result>Integer.MAX_VALUE){
                return Integer.MAX_VALUE;
            }
        }else{
            result=-result;
            if(result<Integer.MIN_VALUE){
                return Integer.MIN_VALUE;
            }
        }
        return result.intValue();
    }

}
