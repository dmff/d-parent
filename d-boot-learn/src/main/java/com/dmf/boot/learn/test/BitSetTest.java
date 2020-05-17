package com.dmf.boot.learn.test;

import org.junit.Test;

import java.io.IOException;
import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author dmf
 * @date 2018/8/26
 *
 * 使用场景：对海量数据进行统计工作，比如日志分析，用户数统计等
 */
public class BitSetTest {


    @Test
    public void test1() throws IOException {
        /**
         * 有一千万个随机数，随机数范围在1-1亿之间，现在要求写出一种算法，将1-1亿之间没有的数求出来
         */
        long start = System.currentTimeMillis();
        Random random=new Random();
        BitSet bitSet=new BitSet(1000000);
        for(int i = 0;i<100000;i++){
            int j = random.nextInt(1000000);
            bitSet.set(j);
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.execute(new CountRescrsiveAction(bitSet,0,1000000));
        System.out.println("消耗时间："+(System.currentTimeMillis()-start));
        //阻塞等待
        //System.in.read();
    }

    public static class CountRescrsiveAction extends RecursiveAction{

        private BitSet bitSet;
        private int threshold = 10000;
        private int start;
        private int end;

        public CountRescrsiveAction(BitSet bitSet,int start, int end) {
            this.start = start;
            this.end = end;
            this.bitSet = bitSet;
        }

        @Override
        protected void compute() {
            if (end-start <= threshold){
                for(int i=start;i<end;i++){
                    if (!bitSet.get(i)){
                        //使用线程池处理
                        System.out.println(i);
                    }
                }
            }else {
                //拆解任务
                int middle = (end-start)/2 + start;
                CountRescrsiveAction left = new CountRescrsiveAction(bitSet, start, middle);
                CountRescrsiveAction right = new CountRescrsiveAction(bitSet, middle,end);
                //使用fork会创建大量线程，使用invokeAll以优化的方式分解及运行任务
                //left.fork();
                //right.fork();
                invokeAll(left,right);
            }
        }
    }

}
