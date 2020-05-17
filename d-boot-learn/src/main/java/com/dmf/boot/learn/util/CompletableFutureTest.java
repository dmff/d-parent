package com.dmf.boot.learn.util;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author dengmingfeng
 * @date 2019/8/15
 */
public class CompletableFutureTest {


    @Test
    public void test1() {
        //异步依赖链式执行
        CompletableFuture.supplyAsync(() -> 1)
                .thenApply(i -> i + 1)
                .thenApply(i -> i * i)
                .whenCompleteAsync((r, e) -> System.out.println(r));

        //等待前几个阶段完成之后返回
        CompletableFuture.supplyAsync(() -> 1)
                .thenApply(i -> i + 1)
                .thenApply(i -> i * i);


    }

    @Test
    public void test2() {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<String> testList = Lists.newArrayList();
        testList.add("cf1");
        testList.add("cf2");
        long start = System.currentTimeMillis();
        CompletableFuture[] cfArr = testList.stream().
                map(t -> CompletableFuture
                        .supplyAsync(() -> pause(t), executor)
                        .whenComplete((result, th) -> {
                            System.out.println("hello" + result);
                        })).toArray(CompletableFuture[]::new);
        // 开始等待所有任务执行完成
        System.out.println("start block");
        CompletableFuture.allOf(cfArr).join();
        System.out.println("block finish, consume time:" + (System.currentTimeMillis() - start));
    }

    public static String pause(String name) {

        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return name;
    }

}
