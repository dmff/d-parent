package com.dmf.bootstarter.switcher;

import java.lang.annotation.*;

/**
 * @author dengmingfeng
 * @date 2020/3/24
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Switcher {

    /**
     * 服务id
     * @return
     */
    String switchId() default "";

    /**
     * 拦截异常抛出
     * @return
     */
    String code() default "000000";

    String msg() default "服务不可用";
}
