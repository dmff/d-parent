package com.dmf.boot.learn.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author dmf
 * @date 2018/8/26
 * <p>
 * ForkJoin的核心是把任务拆解，再把返回值合并的思想
 * 主要有两个类:RecursiveAction、RecursiveTask
 *
 * 而workStealing可以理解为是特殊的ForkJoin线程池，而默认也是采用这种方式
 */
public class ForkJoinTest1 {

    public static void main(String[] args) {
        //单线程测试:20万 3284毫秒  80万：53秒
        singleThread();
        //多线程测试:20万 2729毫秒  80万：25秒
        //multiThread();
    }

    private static void singleThread() {
        //求出20000以内的素数
        Long start = System.currentTimeMillis();
        List<Integer> prime = getPrime(1, 800000);
        prime.forEach(System.out::println);
        System.out.println(System.currentTimeMillis() - start);
    }

    private static void multiThread() {
        //求出20000以内的素数
        Long start = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Task task = new Task(1, 800000);
        forkJoinPool.execute(task);
        List<Integer> list = task.join();
        list.forEach(System.out::println);
        System.out.println(System.currentTimeMillis() - start);
    }


    static boolean isPrime(int num) {
        for (int i = 2; i <= num / 2; i++) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }

    static List<Integer> getPrime(int start, int end) {
        List<Integer> results = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) {
                results.add(i);
            }
        }

        return results;
    }

    static class Task extends RecursiveTask<List<Integer>> {

        private int start;
        private int end;

        public Task(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected List<Integer> compute() {
            if (end - start <= 30000) {
                return getPrime(start, end);
            }

            int mid = (end - start)/2 + start;
            Task left = new Task(start, mid);
            Task right = new Task(mid, end);
            invokeAll(left,right);
            List<Integer> list = left.join();
            list.addAll(right.join());
            return list;
        }
    }
}
