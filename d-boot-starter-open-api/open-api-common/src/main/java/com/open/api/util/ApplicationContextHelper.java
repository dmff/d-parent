package com.open.api.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 从applicationContext中得到Bean 工具类
 */
@Component
public class ApplicationContextHelper implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static Object getBean(String beanName) {
        return context != null ? context.getBean(beanName) : null;
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> var1) {
        return context != null ? context.getBeansWithAnnotation(var1) : null;
    }


}
