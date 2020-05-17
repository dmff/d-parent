package com.dmf.bootstarter.common.annotation;

import com.dmf.bootstarter.common.annotation.handler.*;
import com.tanpin.project.annotation.handler.*;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Type;

/**
 * @author dmf
 * @date 2019/11/4
 */
@Aspect
@Slf4j
public class EFLogAop extends BaseAop {

    private ReturnTypeHandler returnValueHandler = new ReturnResponseResultHandler(new ReturnPageResultHandler(new ReturnSingleResultHandler(new ReturnGeneralTypeHandler(null))));


    @Pointcut("execution(public * com.tanpin..*.*(..))")
    public void publicMethod() {
    }

    @Pointcut("@annotation(com.dmf.bootstarter.common.annotation.EFLog) || @target(com.dmf.bootstarter.common.annotation.EFLog)")
    public void annotationProcessor() {
    }


    @Around("publicMethod() && annotationProcessor()")
    public Object process(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            this.doLog(proceedingJoinPoint, e);
            Type type = this.getGenericReturnType(proceedingJoinPoint);
            return returnValueHandler.handler(type, e);
        }
    }

    private void doLog(ProceedingJoinPoint proceedingJoinPoint, Throwable e) {
        //进行日志打印分发处理
        log.error("处理失败；类名={}，方法={}，异常信息={}");
    }

}
