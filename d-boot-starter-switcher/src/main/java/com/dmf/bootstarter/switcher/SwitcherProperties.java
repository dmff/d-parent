package com.dmf.bootstarter.switcher;

import lombok.Data;

/**
 * @author dengmingfeng
 * @date 2020/3/24
 */
@Data
public class SwitcherProperties {
    /**
     * 开关启用状态
     */
    private Boolean enable;
    /**
     * 开关开启时间：yyyy-MM-dd HH:MM:ss
     */
    private String startTime;
    /**
     * 开关借宿时间
     */
    private String endTime;
}
