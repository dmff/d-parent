package com.dmf.bootstarter.common.annotation.handler;


import com.dmf.bootstarter.common.annotation.result.SingleResult;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author dmf
 * @date 2019/11/4
 */
public class ReturnSingleResultHandler extends AbstractReturnTypeHandler implements ReturnTypeHandler {

    @Override
    public Object handler(Type type, Throwable e) throws Throwable {
        return isSingleResult(type) ? singleResult(e) : nextHandler(type,e);
    }

    private boolean isSingleResult(Type genericReturnType) {
        if (genericReturnType instanceof ParameterizedType){
            Type type = ((ParameterizedType) genericReturnType).getRawType();
            return type.getTypeName().equals(SingleResult.class.getName());
        }else {
            return false;
        }
    }

    private <T> SingleResult singleResult(Throwable e) {
        SingleResult<T> result = new SingleResult<>();
        result.setMessage(e.getMessage());
        result.setStatus("400");
        return result;
    }

    public ReturnSingleResultHandler(ReturnTypeHandler nextReturnTypeHandler) {
        super(nextReturnTypeHandler);
    }
}
