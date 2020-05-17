package com.dmf.bootstarter.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengmingfeng
 * @date 2020/4/20
 */
public abstract class AbstractMyThreadContext {

    private static final Logger log = LoggerFactory.getLogger(AbstractMyThreadContext.class);
    /**
     * logback模板对应的变量
     */
    public final static String MDC_TRACE_ID = "traceId";

    public static final String TRACE_ID = "com.test.trace.MyThreadContext_TRACE_ID_KEY";
    //确保子线程能够继承父线程的数据
    private static final ThreadLocal<Map<Object, Object>> RESOURCES = new InheritableThreadLocalMap<>();

    protected AbstractMyThreadContext() {
    }

    public static Map<Object, Object> getResources() {
        return new HashMap<>(RESOURCES.get());
    }

    public static void setResources(Map<Object, Object> newResources) {
        if (newResources == null || newResources.isEmpty()) {
            return;
        }
        RESOURCES.get().clear();
        RESOURCES.get().putAll(newResources);
    }


    private static Object getValue(Object key) {
        return RESOURCES.get().get(key);
    }


    public static Object get(Object key) {
        if (log.isTraceEnabled()) {
            log.trace("get() - in thread [{}]", Thread.currentThread().getName());
        }

        Object value = getValue(key);
        if ((value != null) && log.isTraceEnabled()) {
            log.trace("Retrieved value of type [{}]  for key [{}] bound to thread [{}]",
                    value.getClass().getName(), key, Thread.currentThread().getName());
        }
        return value;
    }

    public static void put(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }

        if (value == null) {
            remove(key);
            return;
        }

        RESOURCES.get().put(key, value);

        if (log.isTraceEnabled()) {
            log.trace("Bound value of type [{}]  for key [{}]  to thread [{}]",
                    value.getClass().getName(), key, Thread.currentThread().getName());
        }
    }

    public static Object remove(Object key) {
        Object value = RESOURCES.get().remove(key);

        if ((value != null) && log.isTraceEnabled()) {
            log.trace("Retrieved value of type [{}]  for key [{}] from thread [{}]",
                    value.getClass().getName(), key, Thread.currentThread().getName());
        }

        return value;
    }

    public static void remove() {
        RESOURCES.remove();
    }

    //从线程局部变量中获取TraceId
    public static String getTraceId() {
        return (String) get(TRACE_ID);
    }

    //将TraceId摄入线程局部变量中
    public static void setTraceId(String xid) {
        put(TRACE_ID, xid);
    }

    //清除线程局部变量中的TraceId
    public static void removeTraceId() {
        remove(TRACE_ID);
    }

    private static final class InheritableThreadLocalMap<T extends Map<Object, Object>> extends InheritableThreadLocal<Map<Object, Object>> {
        @Override
        protected Map<Object, Object> initialValue() {
            return new HashMap<>(1 << 4);
        }

        @SuppressWarnings({"unchecked"})
        @Override
        protected Map<Object, Object> childValue(Map<Object, Object> parentValue) {
            if (parentValue != null) {
                return (Map<Object, Object>) ((HashMap<Object, Object>) parentValue).clone();
            } else {
                return null;
            }
        }
    }

}
