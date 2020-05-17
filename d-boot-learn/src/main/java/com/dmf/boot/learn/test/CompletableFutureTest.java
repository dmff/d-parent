package com.dmf.boot.learn.test;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author dmf
 * @date 2019/12/8
 */
public class CompletableFutureTest {

    @Test
    public void test(){
        CompletableFuture.supplyAsync(()->1)
                .thenApply(i->i+1)
                .thenApply(i->i*i)
                .whenComplete((r,e)-> System.out.println(r));
    }


    @Test
    public void test2(){
        CompletableFuture.supplyAsync(()->"hello")
                .thenApply(s->s+"world")
                .thenApply(String::toUpperCase)
                .thenCombine(CompletableFuture.completedFuture("java"),(s1,s2)->s1+s2)
                .thenAccept(System.out::println);
    }

    @Test
    public void test3(){
        CompletableFuture.supplyAsync(()->"hello")
                .thenApplyAsync(s->s+"world")
                .thenApplyAsync(String::toUpperCase)
                .thenCombine(CompletableFuture.completedFuture("java"),(s1,s2)->s1+s2)
                .thenAccept(System.out::println);
    }


}
