package com.dmf.bootstarter.common.annotation.result;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dmf
 * @date 2019/11/4
 */
@Getter
@Setter
public class SingleResult<T> extends Result {

    private T data;

}
