package com.dmf.bootstarter.common.annotation.handler;


import com.dmf.bootstarter.common.annotation.result.Response;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author dmf
 * @date 2019/11/4
 */
@Slf4j
public class ReturnResponseResultHandler extends AbstractReturnTypeHandler implements ReturnTypeHandler {

    @Override
    public Object handler(Type type, Throwable e) throws Throwable {
        if (isResponse(type)){
            return this.isBoolResponse((ParameterizedType)type) ? this.boolFailed(e) : this.failed(e);
        }else {
            return this.nextHandler(type, e);
        }
    }


    private boolean isBoolResponse(ParameterizedType genericReturnType) {
        Type[] actualTypeArguments = genericReturnType.getActualTypeArguments();
        return actualTypeArguments.length == 1 && Boolean.class.equals(actualTypeArguments[0]);
    }

    private boolean isResponse(Type genericReturnType) {
        if (genericReturnType instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType)genericReturnType).getRawType();
            return rawType.getTypeName().equals(Response.class.getName());
        } else {
            return false;
        }
    }


    protected <T> Response<T> failed(Throwable e) {
        Response<T> response = new Response<>();
        String message = e.getMessage();
        // TODO 是否需要设置异常code
        response.setMessage(message);
        response.failed();
        return response;
    }

    private  Response boolFailed(Throwable e) {
        Response response = failed(e);
        response.setData(false);
        return response;
    }

    public ReturnResponseResultHandler(ReturnTypeHandler nextReturnTypeHandler) {
        super(nextReturnTypeHandler);
    }
}
