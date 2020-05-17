package com.dmf.bootstarter.common.enums;

import lombok.AllArgsConstructor;

/**
 * @author dmf
 * @date 2019/9/2
 */
@AllArgsConstructor
public enum  UserTypeEnum implements EFEnum<String,String>{
    /**
     * 普通用户
     */
    Ordinary("1","普通用户"),
    /**
     * 公司管理用户
     */
    Admin("2","管理员用户"),
    /**
     * 平台用户
     */
    PLAT("3","平台")
    ;

    private String code;
    private String type;


    @Override
    public String getKey() {
        return this.code;
    }

    @Override
    public String getValue() {
        return this.type;
    }
}
