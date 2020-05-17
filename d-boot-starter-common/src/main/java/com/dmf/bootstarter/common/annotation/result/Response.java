package com.dmf.bootstarter.common.annotation.result;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author dmf
 * @date 2019/11/4
 */
@Getter
@Setter
public class Response<T> implements Serializable {

    private String code;
    private String message;
    private T data;
    private String status;

    public Response<T> success() {
        this.setStatus("200");
        return this;
    }

    public Response<T> failed() {
        this.setStatus("400");
        return this;
    }
}
