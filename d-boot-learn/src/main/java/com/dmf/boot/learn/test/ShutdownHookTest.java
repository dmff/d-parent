package com.dmf.boot.learn.test;

import java.util.concurrent.TimeUnit;

/**
 * @author dmf
 * @date 2018/9/20
 */
public class ShutdownHookTest {
    /**
     * 程序退出的回调函数：
     *      1.程序正常退出
     *      2.使用System.exit()
     *      3.终端使用ctrl+c触发的中断
     *      4.系统关闭
     *      5.OutOfMemory 宕机
     *      6.使用 Kill pid 命令干掉进程（注：在使用 kill -9 pid 时，是不会被调用的）
     */

    public static void addHook(){
        Runtime.getRuntime()
                .addShutdownHook(new Thread(()-> System.out.println("Execute Hook....."),"hook-thread"));
    }

    public static void main(String[] args) throws InterruptedException {
        addHook();
        System.out.println("doing someThing");
        TimeUnit.SECONDS.sleep(2);
    }
}
