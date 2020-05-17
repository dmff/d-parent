package com.dmf.boot.learn.test;

import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * @author dmf
 * @date 2018/8/26
 *
 * 工作窃取，当其他线程的任务已经完成时会去获取另一个线程的任务执行
 */
public class ForkJoinTest3 {

    public static void main(String[] args) throws IOException {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4,
                ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                null,
                true);

        System.out.println(Runtime.getRuntime().availableProcessors());

        forkJoinPool.execute(new Action(1000));
        forkJoinPool.execute(new Action(2000));
        forkJoinPool.execute(new Action(2000));
        forkJoinPool.execute(new Action(2000));
        forkJoinPool.execute(new Action(2000));

        //由于产生的是精灵线程（守护线程、后台线程），主线程不阻塞的话，看不到输出
        System.in.read();
    }

    static class Action implements Runnable {
        int time;

        Action(int t) {
            this.time = t;
        }

        @Override
        public void run() {

            try {
                TimeUnit.MILLISECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(time  + " " + Thread.currentThread().getName());
        }
    }
}
