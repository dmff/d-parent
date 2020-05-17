package com.dmf.bootstarter.switcher;

import lombok.Getter;

/**
 * @author dengmingfeng
 * @date 2020/3/24
 */
@Getter
public class SwitcherException extends RuntimeException {

    private final String code;
    private final String msg;

    public SwitcherException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
