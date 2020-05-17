package com.dmf.boot.learn.test;

import org.junit.Test;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author dmf
 * @date 2018/8/26
 */
public class ForkJoinTest2 {

    /**
     *  求1000000万个随机100以内的数的和
     */
    static int[] nums = new int[10000000];
    static Random r = new Random();

    static {
        for(int i=0; i<nums.length; i++) {
            nums[i] = r.nextInt(100);
        }
    }

    @Test
    public void test() throws Exception{
        //test1();
        //test2();
        test3();
    }


    /**
     * 1000000:137毫秒，结果49511104
     */
    public void test1() {
        long l1 = System.currentTimeMillis();
        int sum = Arrays.stream(nums).sum();
        System.out.println(System.currentTimeMillis()-l1);
        System.out.println(sum);
    }

    /**
     * 1000000:12毫秒，结果49511104、49543000
     */
    public void test2() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        long l2 = System.currentTimeMillis();
        ForkJoinTask<Integer> task = forkJoinPool.submit(new Task(0, 10000000, nums));
        Integer integer = task.get();
        System.out.println(System.currentTimeMillis()-l2);
        System.out.println(integer);
    }

    public void test3() throws InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        long l2 = System.currentTimeMillis();
        forkJoinPool.execute(new Action(0, 10000000, nums));
        forkJoinPool.awaitTermination(10,TimeUnit.SECONDS);
        System.out.println(System.currentTimeMillis()-l2);
    }

    static class Task extends RecursiveTask<Integer>{
        int start;
        int end;
        int[] nums;
        public Task(int start, int end,int[] nums) {
            this.start = start;
            this.end = end;
            this.nums = nums;
        }

        @Override
        protected Integer compute() {
            int sum = 0;
            if (end-start < 50000){
                for(int i = start;i<end;i++){
                    sum +=nums[i];
                }
                return sum;
            }

            int mid = (end-start)/2 +start;
            Task task1 = new Task(start, mid, nums);
            Task task2 = new Task(mid,end, nums);
            invokeAll(task1,task2);
            return task1.join() + task2.join();
        }
    }

    static class Action extends RecursiveAction{
        int start;
        int end;
        int[] nums;
        public Action(int start, int end,int[] nums) {
            this.start = start;
            this.end = end;
            this.nums = nums;
        }

        @Override
        protected void compute() {
            int sum = 0;
            if (end-start < 50000){
                for(int i = start;i<end;i++){
                    sum +=nums[i];
                }
                System.out.println("from:" + start + " to:" + end + " = " + sum);
            }

            int mid = (end-start)/2 +start;
            Action action1 = new Action(start, mid, nums);
            Action action2 = new Action(mid,end, nums);
            action1.fork();
            action2.fork();
            //invokeAll(action1,action2);
        }
    }
}
