package com.dmf.boot.learn.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池异常测试
 * @author dmf
 * @date 2020/3/8
 */
public class ThreadExceptionTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(ThreadExceptionTest.class);


    public static void main(String[] args) throws Exception{
        ExecutorService executorService = new ThreadPoolExecutor(1,1,
                0L, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>());

        executorService.execute(()->LOGGER.info("=======1111111111111============="));
        TimeUnit.SECONDS.sleep(5);


        executorService.execute(()->{
            int count = 0;
            while (true) {
                count++;
                LOGGER.info("-------222-------------{}", count);

                if (count == 10) {
//                    try {
                    System.out.println(1 / 0);
                    try {
                    } catch (Exception e) {
                        LOGGER.error("Exception",e);
                    }
                }

                if (count == 20) {
                    LOGGER.info("count={}", count);
                    break;
                }
            }
        });


        TimeUnit.SECONDS.sleep(5);
        //
        executorService.execute(()->LOGGER.info("run222222222"));
        //execute.shutdown();
    }



}
