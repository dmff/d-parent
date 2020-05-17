package com.dmf.bootstarter.common.annotation.handler;

import java.lang.reflect.Type;

/**
 * @author dmf
 * @date 2019/11/4
 */
public class ReturnGeneralTypeHandler extends AbstractReturnTypeHandler implements ReturnTypeHandler {

    @Override
    public Object handler(Type type, Throwable e) throws Throwable {
        throw e;
    }

    public ReturnGeneralTypeHandler(ReturnTypeHandler nextReturnTypeHandler) {
        super(nextReturnTypeHandler);
    }
}
