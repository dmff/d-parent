package com.dmf.boot.learn.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author dmf
 * @date 2019/12/8
 */
public class CompletableFutureOrderTest {

    /**
     * 今日订单数
     *
     * @return
     */
    public CompletableFuture<String> todayOrderCount() {
        return CompletableFuture.supplyAsync(this::getTodayOrderCount);
    }

    public CompletableFuture<String> todayTurnover() {
        return CompletableFuture.supplyAsync(this::getTodayTurnover);
    }

    public CompletableFuture<String> totalTurnover() {
        return CompletableFuture.supplyAsync(this::getTotalTurnover);
    }

    private String getTodayOrderCount() {
        System.out.println(">>>>>>> 查询今日订单数:" + Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "50";
    }

    /**
     * 今日交易额
     *
     * @return
     */
    private String getTodayTurnover() {
        System.out.println(">>>>>>> 查询今日交易额:" + Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "200";
    }

    /**
     * 总交易额
     *
     * @return
     */
    private String getTotalTurnover() {
        System.out.println(">>>>>>> 查询总交易额:" + Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "800";
    }


    private Map<String,Object> orderReport() {
        CompletableFuture<String> todayOrderCountFuture = this.todayOrderCount();
        CompletableFuture<String> todayTurnoverFuture = this.todayTurnover();
        CompletableFuture<String> totalTurnoverFuture = this.totalTurnover();

        Map<String,Object> map = new HashMap<>(4);
        todayOrderCountFuture.whenComplete((v, t) -> map.put("todayOrderCountFuture", v));
        todayTurnoverFuture.whenComplete((v, t) -> map.put("todayTurnoverFuture", v));
        totalTurnoverFuture.whenComplete((v, t) -> map.put("totalTurnoverFuture", v));

//        allOf(Lists.newArrayList(todayOrderCountFuture,todayTurnoverFuture,totalTurnoverFuture));
        CompletableFuture.allOf(todayOrderCountFuture, todayTurnoverFuture, totalTurnoverFuture).join();
        System.out.println("统计数据完成完成！！！！");
        return map;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Map<String, Object> map = new CompletableFutureOrderTest().orderReport();
        System.out.println(map);
        System.out.println("耗时:" + (System.currentTimeMillis() - start));
    }

    public <T> CompletableFuture<List<T>> allOf(List<CompletableFuture<T>> futuresList) {
        CompletableFuture<?>[] completableFutures = new CompletableFuture<?>[futuresList.size()];
        futuresList.toArray(completableFutures);
        CompletableFuture<Void> allFuturesResult = CompletableFuture.allOf(completableFutures);
        return allFuturesResult.thenApply(v ->
                futuresList.stream().
                        map(future -> future.join()).
                        collect(Collectors.<T>toList())
        );
    }
}
