package com.dmf.boot.learn.algorithms;

import org.junit.Test;

/**
 * @author dmf
 * @date 2018/8/9
 */
public class ArrayTest {

    /**
     * 二维数组长度一样，并且从左往右、从上往下递增，如何快速判断一个数是否在二维数组里面存在
     * 解题思路：采用二分查找法
     */
    public boolean find(int target,int[][] array){
        int i = 0;
        int j = array[i].length-1;
        while(i<array.length && j>=0 ) {
            if (array[i][j] == target) {
                return true;
            } else if (array[i][j] > target) {
                j--;
            } else {
                i++;
            }
        }
        return false;
    }

    @Test
    public void test1(){
        int arr[][] = new int[][]{new int[]{1,2,3},new int[]{4,5,6},new int[]{7,8,9}};
        System.out.println(find(7,arr));
    }
}
