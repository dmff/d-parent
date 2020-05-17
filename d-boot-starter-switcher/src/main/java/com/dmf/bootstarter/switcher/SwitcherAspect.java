package com.dmf.bootstarter.switcher;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author dengmingfeng
 * @date 2020/3/24
 */
@Slf4j
@Aspect
@Component
public class SwitcherAspect {

    @Autowired
    private SwitcherHelper switcherHelper;

    @Before("@annotation(Switcher)")
    public void switcher(JoinPoint point) {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        Switcher switcher = method.getAnnotation(Switcher.class);
        String switcherId = switcher.switchId();
        if (switcherHelper.isEnable(switcherId) && switcherHelper.isValid(switcherId)) {
            log.warn("当前服务不可用;switcherId={},code={},msg={}", switcherId, switcher.code(), switcher.msg());
            throw new SwitcherException(switcher.code(), switcher.msg());
        }
    }

}
