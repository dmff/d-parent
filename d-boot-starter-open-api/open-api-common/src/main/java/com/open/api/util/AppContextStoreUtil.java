package com.open.api.util;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import java.util.Map;

public class AppContextStoreUtil {

    private static ThreadLocal<Map<String, Object>> threadContext = new ThreadLocal<>();

    private static volatile AppContextStoreUtil instance = null;

    private AppContextStoreUtil() {
    }

    public static AppContextStoreUtil getInstance() {
        if (instance == null) {
            synchronized (AppContextStoreUtil.class) {
                if (instance == null) {
                    instance = new AppContextStoreUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 设置线程上下文键值
     *
     * @param key
     * @param o
     */
    public static void set(final String key, final Object o) {
        Map<String, Object> map = threadContext.get();

        if (Objects.equal(null, map)) {
            map = Maps.newHashMap();
        }
        map.put(key, o);
        threadContext.set(map);
    }

    /**
     * 设置线程上下文键值
     *
     * @param key
     */
    public static void removeKey(String key) {
        Map<String, Object> map = threadContext.get();
        if (Objects.equal(null, map)) {
            map = Maps.newHashMap();
        }
        map.remove(key);
        threadContext.set(map);
    }

    /**
     * 获得线程上下文对应key的值
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        Map<String, Object> map = threadContext.get();
        if (Objects.equal(null, map)) {
            return null;
        } else {
            return map.get(key);
        }
    }

    /**
     * 清除线程上下文
     *
     * @return
     */
    public static void clean() {
        Map<String, Object> map = threadContext.get();
        if (!Objects.equal(null, map)) {
            map.clear();
            threadContext.set(map);
        }
    }

}
