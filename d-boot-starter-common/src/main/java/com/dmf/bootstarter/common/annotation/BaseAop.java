package com.dmf.bootstarter.common.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 获取全限定类名、方法名、和返回值类型、方法参数名称和犯法参数值
 *
 * @author dmf
 * @date 2019/9/2
 */
public class BaseAop {


    protected Class<?> getClass(ProceedingJoinPoint joinPoint) throws ClassNotFoundException {
        String classType = joinPoint.getTarget().getClass().getName();
        return Class.forName(classType);
    }

    protected String getClassName(ProceedingJoinPoint joinPoint) throws ClassNotFoundException {
        return getClass(joinPoint).getName();
    }

    protected String getClassSimpleName(ProceedingJoinPoint joinPoint) throws ClassNotFoundException {
        return getClass(joinPoint).getSimpleName();
    }

    protected Method getMethod(ProceedingJoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod();
    }

    protected String getMethodName(ProceedingJoinPoint joinPoint) {
        return getMethod(joinPoint).getName();
    }

    protected Type getGenericReturnType(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        return this.getMethod(joinPoint).getGenericReturnType();
    }

    protected List<Class<?>> getFieldsType(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        Class<?>[] parameterTypes = this.getMethod(joinPoint).getParameterTypes();
        List<Class<?>> list = new ArrayList<>();
        if (parameterTypes != null && parameterTypes.length > 0) {
            Collections.addAll(list, parameterTypes);
        }
        return list;
    }


    protected String getPackageName(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String[] packageName = signature.getDeclaringTypeName().split("\\.");
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < packageName.length; ++i) {
            if (i < packageName.length - 1) {
                stringBuilder.append(packageName[i].substring(0, 1));
            } else {
                stringBuilder.append(packageName[i]);
            }

            stringBuilder.append(".");
        }

        return stringBuilder.toString();
    }
}
