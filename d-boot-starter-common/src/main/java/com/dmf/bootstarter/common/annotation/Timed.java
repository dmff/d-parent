package com.dmf.bootstarter.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author dmf
 * @date 2019/9/2
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Timed {

    long thredhold() default 500L;
}
