package com.dmf.bootstarter.common.annotation;

import com.beilie.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author dmf
 * @date 2019/9/2
 */
@Aspect
@Slf4j
public class TimedAOP extends BaseAop {

    @Pointcut("execution(public * com.tanpin..*.*(..))")
    public void publicMethod() {
    }

    /**
     * @annotation用于匹配@Timed方法上的注解
     * @target用于匹配@Timed类上的注解
     */
    @Pointcut("@annotation(com.dmf.bootstarter.common.annotation.Timed) || @target(com.dmf.bootstarter.common.annotation.Timed)")
    public void annotationProcessor() {
    }

    @Around("publicMethod() && annotationProcessor()")
    public Object process(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();
        try {
            Object proceed = proceedingJoinPoint.proceed();
            this.isPrintTimedLog(startTime, proceedingJoinPoint);
            return proceed;
        } finally {
            this.isPrintTimedLog(startTime, proceedingJoinPoint);
        }
    }

    private void isPrintTimedLog(Long startTime, ProceedingJoinPoint proceedingJoinPoint) throws NoSuchMethodException {
        long endTime = System.currentTimeMillis();
        Timed timed = getTimed(proceedingJoinPoint);
        if (endTime - startTime > timed.thredhold()) {
            //todo 打印全类名、方法名、阈值、耗时
            log.info("超出时间阈值了：time={}ms", endTime - startTime);
        }
    }

    private Timed getTimed(ProceedingJoinPoint proceedingJoinPoint) throws NoSuchMethodException {
        String methodName = proceedingJoinPoint.getSignature().getName();
        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        if (method.getDeclaringClass().isInterface()) {
            method = proceedingJoinPoint.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());
        }

        Timed timed = null;
        if (method.isAnnotationPresent(Timed.class)) {
            timed = method.getAnnotation(Timed.class);
        } else {
            Object target = proceedingJoinPoint.getTarget();
            if (target.getClass().isAnnotationPresent(Timed.class)) {
                timed = target.getClass().getAnnotation(Timed.class);
            }
        }

        return timed;
    }


    public static void main(String[] args) {
        System.out.println("111");
    }

}
