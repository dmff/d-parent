package com.dmf.bootstarter.logger;

import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author dmf
 * @date 2020/5/20 22:48
 */
public class MdcThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    @Override
    public void execute(Runnable runnable) {
        // 获取父线程MDC中的内容，必须在run方法之前，否则等异步线程执行的时候有可能MDC里面的值已经被清空了，这个时候就会返回null
        Map<String, String> context = MDC.getCopyOfContextMap();
        super.execute(() -> run(runnable, context));
    }

    @Override
    public Future<?> submit(Runnable task) {
        Map<String, String> context = MDC.getCopyOfContextMap();
        return super.submit(()->run(task,context));
    }

    /**
     * 子线程委托的执行方法
     *
     * @param runnable {@link Runnable}
     * @param context  父线程MDC内容
     */
    private void run(Runnable runnable, Map<String, String> context) {
        // 将父线程的MDC内容传给子线程
        MDC.setContextMap(context);
        try {
            // 执行异步操作
            runnable.run();
        } finally {
            // 清空MDC内容
            MDC.clear();
        }
    }

}
