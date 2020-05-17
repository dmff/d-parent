package com.dmf.bootstarter.common.cache;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author dmf
 * @date 2019/12/2
 */
@Slf4j
public abstract class AbstractCache<V> implements EFCache<V>  {


    protected String name;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public ValueWrapper get(Object o) {
        return null;
    }

    @Override
    public <T> T get(Object o,  Class<T> aClass) {
        return null;
    }

    @Override
    public <T> T get(Object o, Callable<T> callable) {
        return null;
    }
}
