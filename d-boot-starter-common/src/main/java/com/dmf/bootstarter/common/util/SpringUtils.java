package com.dmf.bootstarter.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author dmf
 * @date 2019/7/21
 */
public class SpringUtils implements ApplicationContextAware {

    private  static ApplicationContext applicationContext;
    Lock lock = new ReentrantLock();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtils.applicationContext ==null) {
            SpringUtils.applicationContext = applicationContext;
        }

        lock.unlock();
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过Class类型获取bean对象
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过beanName和Class获取bean对象，这种一般用于class类型有多个bean对象的场景用
     * @param beanName
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanName,Class<T> clazz) {
        return getApplicationContext().getBean(beanName,clazz);
    }
}
