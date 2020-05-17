package com.dmf.boot.learn.algorithms;

/**
 * @author dmf
 * @date 2018/10/7
 */
public class AlgoaithmTest {

    /**
     * 菲比那切数列:递归实现(数据一大容易出现栈溢出的情况，最好使用迭代)
     *
     * @param n
     * @return
     */
    public int fibonacci(int n) {
        if (n < 2) {
            return n;
        }

        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    /**
     * 菲比那切数列：赋值实现
     *
     * @param n
     * @return
     */
    public int fibonacci2(int n) {
        if (n < 2) {
            return n;
        }

        int a = 0, b = 1, res = 0;
        while (n-- >= 2) {
            res = a + b;
            a = b;
            b = res;
        }
        return res;
    }

    /**
     * 青蛙跳台阶的问题：青蛙可以一次跳一级台阶，也可以一次跳两级台阶
     *      经分析0,1,2,3,5,跳的n级台阶可以从n-1跳上来，也可以从n-2跳上来
     * @param target
     * @return
     */
    public int jumpFloor(int target) {
        if(target<=2){
            return target;
        }
        int a=1,b=2,res=0;
        while (res-->2){
            res = a + b;
            a = b;
            b = res;
        }
        return res;
    }

    /**
     * 青蛙跳台阶的问题：一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级
     *      经分析：1,2,4,8
     *      每个台阶都有跳与不跳两种情况（除了最后一个台阶），最后一个台阶必须跳。所以共用2^(n-1)中情况
     * @param target
     * @return
     */
    public int jumpFloorII(int target) {
        if (target<2){
            return target;
        }
        return 2 * jumpFloorII(target-1);
    }


    /**
     * 1个数在二进制中出现1的次数：
     *      直接遍历所有的位数(32)
     *      遍历当前位数
     * @param n
     * @return
     */
    public int numberOf1(int n){
        int count = 0;
        for (int i=0;i<32;i++){
            if (((n>>i) & 1) ==1){
                count++;
            }
        }
        return count;
    }

    public int numberOf12(int n){
        int count = 0;
        while (n !=0){
            count++;
            n = n & n-1;
        }
        return count;
    }

    /**
     * 计算一个浮点数的n次方
     * @return
     */
    public double power(double base,int exponent){
        if (exponent ==0){
            return 1;
        }

        int n = exponent;
        if (n<0){
            if (base==0){
                throw new RuntimeException("分母不可以为0");
            }
            n = -exponent;
        }

        //直接for循环累乘，时间复杂度为o(n)
        //整数幂的方式,复杂度为o(log n)
        double ans = 1.0d;
        while (n != 0){
            if ((n & 1) != 0){
                ans *= base;
            }
            base *= base;
            n >>=1;
        }
        return exponent > 0 ? ans : 1/ans;
    }



    public static void main(String[] args) {
        int fibonacci = new AlgoaithmTest().fibonacci(1000000);
        System.out.println(fibonacci);
    }
}
