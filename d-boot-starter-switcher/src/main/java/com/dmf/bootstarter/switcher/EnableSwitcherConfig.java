package com.dmf.bootstarter.switcher;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author dengmingfeng
 * @date 2020/3/25
 */

@EnableAspectJAutoProxy(proxyTargetClass = true)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(value = {SwitcherHelper.class})
@EnableConfigurationProperties(value = {SwitcherContainer.class,SwitcherHelper.class})
@Documented
public @interface EnableSwitcherConfig {
}
