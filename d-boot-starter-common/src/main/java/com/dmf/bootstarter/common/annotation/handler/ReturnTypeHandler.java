package com.dmf.bootstarter.common.annotation.handler;

import java.lang.reflect.Type;

/**
 * @author dmf
 * @date 2019/11/4
 */
public interface ReturnTypeHandler {


    Object handler(Type type, Throwable var2) throws Throwable;

}
