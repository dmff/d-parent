package com.dmf.bootstarter.common.annotation.handler;


import com.dmf.bootstarter.common.annotation.result.PageResult;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author dmf
 * @date 2019/11/4
 */
public class ReturnPageResultHandler extends AbstractReturnTypeHandler implements ReturnTypeHandler {

    @Override
    public Object handler(Type type, Throwable e) throws Throwable {
        return isPageResult(type) ? pageResult(e) : nextHandler(type,e);
    }

    private boolean isPageResult(Type genericReturnType) {
        if (genericReturnType instanceof ParameterizedType){
            Type type = ((ParameterizedType) genericReturnType).getRawType();
            return type.getTypeName().equals(PageResult.class.getName());
        }else {
            return false;
        }
    }

    private <T> PageResult<T> pageResult(Throwable e) {
        PageResult<T> result = new PageResult<>();
        result.setMessage(e.getMessage());
        result.setStatus("400");
        return result;
    }

    public ReturnPageResultHandler(ReturnTypeHandler nextReturnTypeHandler) {
        super(nextReturnTypeHandler);
    }
}
