package com.dmf.boot.learn.algorithms;

import java.util.Stack;

/**
 * @author dmf
 * @date 2020/3/15 16:05
 *
 * 求一个数距离最短的对称数
 */
public class DuiChenNum {

    public static void main(String[] args) {
        for(int i=205;i<300;i++){
            printDuiChenShu(i);
        }
    }

    /**
     * 经分析，个位数肯定是对称数，所以范围是0-2n
     * @param n
     */
    public static void printDuiChenShu(int n){
        if (n<0){
            System.out.println("输入错误");
        }

        int d1 = 0;
        int d2 = 0;
        //肯定可以找到一个对称数
        for(int i=n-1;i>0;i--){
            if (isDuiChenShu(i)){
                d1 = i;
                break;
            }
        }

        for(int i=n+1;i<2*n-d1;i++){
            if(isDuiChenShu(i)){
                d2 = i;
                break;
            }
        }

        if (d2 == 0){
            System.out.println(d1);
        }else {
            if (n - d1 < d2 - n) {
                System.out.println(d1);
            } else if (n - d1 > d2 - n) {
                System.out.println(d2);
            } else {
                System.out.println(d1 + "," + d2);
            }
        }
    }

    public static boolean isDuiChenShu(int n){
        String s = String.valueOf(n);
        for(int i=0;i<s.length()/2;i++){
            if (s.charAt(i) != s.charAt(s.length()-1-i)){
                return false;
            }
        }
        return true;
    }



}
