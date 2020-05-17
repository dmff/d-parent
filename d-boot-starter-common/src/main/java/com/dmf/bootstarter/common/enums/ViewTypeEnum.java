package com.dmf.bootstarter.common.enums;

import lombok.AllArgsConstructor;


/**
 * @author dmf
 * @date 2019/9/2
 */
@AllArgsConstructor
public enum  ViewTypeEnum implements EFEnum<Integer,String> {

    /**
     * 列表
     */
    List(0,"list"),
    /**
     * 详情
     */
    Detail(1,"detail")
    ;

    private Integer code;
    private String type;

    @Override
    public Integer getKey() {
        return this.code;
    }

    @Override
    public String getValue() {
        return this.type;
    }
}
