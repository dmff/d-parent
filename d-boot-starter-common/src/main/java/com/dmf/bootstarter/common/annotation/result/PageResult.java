package com.dmf.bootstarter.common.annotation.result;

import com.github.pagehelper.PageInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dmf
 * @date 2019/11/4
 */
@Getter
@Setter
public class PageResult<T> extends Result {

    private PageInfo<T> data;

}
