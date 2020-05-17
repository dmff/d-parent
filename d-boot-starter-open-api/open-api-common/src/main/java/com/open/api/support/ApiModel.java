package com.open.api.support;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * api接口对象
 */
@Data
public class ApiModel {

    /**
     * 类 spring bean
     */
    private String beanName;

    /**
     * 方法对象
     */
    private Method method;

    private Class<?> param;


    public ApiModel(String beanName, Method method,Class<?> param) {
        this.beanName = beanName;
        this.method = method;
        this.param = param;
    }
}
