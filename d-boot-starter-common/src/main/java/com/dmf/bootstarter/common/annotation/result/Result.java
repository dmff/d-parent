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
public abstract class Result implements Serializable {

    private String status;

    private String message;

}
