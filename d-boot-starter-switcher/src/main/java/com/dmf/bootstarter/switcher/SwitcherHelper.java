package com.dmf.bootstarter.switcher;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author dengmingfeng
 * @date 2020/3/24
 */
@Slf4j
public class SwitcherHelper {

    @Autowired
    private SwitcherContainer switcherContainer;

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 判断服务开关是否开启
     *
     * @param switcherId
     * @return true-开启；false-没开启
     */
    public boolean isEnable(String switcherId) {
        SwitcherProperties switcherProperties = switcherContainer.getConfigMaps().get(switcherId);
        if (switcherProperties != null && Boolean.TRUE.equals(switcherProperties.getEnable())) {
            return true;
        }

        return false;
    }

    /**
     * 判断开关是否在生效时间内，配合isEnable 为true使用,如果没有配置或者配置错误，则开关完全由enable控制
     *
     * @param switcherId
     * @return true-生效，false-未生效
     */
    public boolean isValid(String switcherId) {
        SwitcherProperties switcherProperties = switcherContainer.getConfigMaps().get(switcherId);
        if (!StringUtils.isEmpty(switcherProperties.getStartTime()) && !StringUtils.isEmpty(switcherProperties.getEndTime())) {
            try {
                LocalDateTime startTime = LocalDateTime.parse(switcherProperties.getStartTime(), DateTimeFormatter.ofPattern(DATE_FORMAT));
                LocalDateTime endTime = LocalDateTime.parse(switcherProperties.getEndTime(), DateTimeFormatter.ofPattern(DATE_FORMAT));
                return LocalDateTime.now().isBefore(endTime) && LocalDateTime.now().isAfter(startTime);
            } catch (Exception e) {
                log.warn("配置文件生效期配置错误，请检查格式是否为{}", DATE_FORMAT);
                return true;
            }
        }
        return true;
    }
}
