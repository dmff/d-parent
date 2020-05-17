package com.dmf.boot.learn.algorithms;

/**
 * 瓶子瓶盖换饮料问题
 */
public class BottleTest {

    public static void main(String[] args) {
        getNum(20);
    }

    public static void getNum(int n) {
        //把所有钱拿去买饮料
        int sum = n;
        int x = n;
        int y = n;
        //循环判断瓶盖和瓶子数是否可以替换
        while(x>=2 || y>=3) {
            //把所有的瓶子换
            if (x >= 2 ) {
                int n1 = x / 2;
                sum += n1;
                x = x % 2 + n1;
                y += n1;
            }

            //把所有的瓶盖换
            if (y >= 3) {
                int n2 = y / 3;
                sum += n2;
                x += n2;
                y = y % 3 + n2;
            }
        }
        System.out.println(String.format("sum:%s;x:%s;y:%s",sum,x,y));
    }

}
