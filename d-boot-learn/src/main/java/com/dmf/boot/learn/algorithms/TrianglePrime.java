package com.dmf.boot.learn.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * 执教三角形为三素数的组合
 * @author dmf
 * @date 2020/4/1 22:03
 */
public class TrianglePrime {


    public static void main(String[] args) {

    }

    public List<List<Integer>> prime(int start,int end){
        List<List<Integer>> list = new ArrayList<>();
        for(int i = start;i<=end-2;i++){
            for(int j=start+1;j<=end-1;j++){
                //找到符合直角三角形的数
                double x = Math.pow(i,2) + Math.pow(j,2);
                int c = (int) Math.sqrt(x);
                if(c<= end && isPrime(i,j) && isPrime(i,c) && isPrime(j,c)){
                    List<Integer> list1 = new ArrayList<>();
                    list1.add(i);
                    list1.add(j);
                    list1.add(c);
                    list.add(list1);
                }
            }
        }
        return list;
    }

    /**
     * 判断是否互为质素
     * @param i
     * @param j
     * @return
     */
    private boolean isPrime(int i, int j) {
        int tmp = 0;
        if(i>j){
            tmp = i;
            i = j;
            j = tmp;
        }

        int k = 0;
        while((k = j%i) !=0){
            j = i;
            i = k;
        }

        return i == 1;
    }

}
