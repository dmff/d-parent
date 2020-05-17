package com.dmf.bootstarter.logger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author dengmingfeng
 * @date 2020/4/20
 */
@Configuration
@ConditionalOnClass(value = DispatcherServlet.class)
public class MdcFilterAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(MdcFilter.class)
    public MdcFilter mdcFilter() {
        return new MdcFilter();
    }
}
