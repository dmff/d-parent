package com.dmf.boot.learn.algorithms;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author dmf
 * @date 2020/3/29 13:00
 *
 * 全排列
 */
public class AllRange {


    public static void main(String[] args) {
        perm(new int[]{1,2,3,4},0,3);
    }


    public static void perm(int[] array,int start,int end){
        if(start==end){
            System.out.println(Arrays.toString(array));
            return;
        }

        for(int i = start;i<=end;i++){
            swap(array,start,i);
            perm(array,start+1,end);
            swap(array,start,i);
        }
        Queue<Integer> pq = new PriorityQueue<>((v1, v2) -> v2 - v1);
    }


    public static void swap(int[] array,int i,int j){
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }


}
