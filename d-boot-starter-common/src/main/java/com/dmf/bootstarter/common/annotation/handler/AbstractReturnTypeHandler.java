package com.dmf.bootstarter.common.annotation.handler;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;

/**
 * @author dmf
 * @date 2019/11/4
 */
@Getter
@Setter
public abstract class AbstractReturnTypeHandler {

    protected ReturnTypeHandler nextReturnTypeHandler;

    protected Object nextHandler(Type type, Throwable e) throws Throwable {
        return this.nextReturnTypeHandler != null ? this.nextReturnTypeHandler.handler(type, e) : null;
    }

    public AbstractReturnTypeHandler(ReturnTypeHandler nextReturnTypeHandler) {
        this.nextReturnTypeHandler = nextReturnTypeHandler;
    }

    public AbstractReturnTypeHandler() {
    }
}
