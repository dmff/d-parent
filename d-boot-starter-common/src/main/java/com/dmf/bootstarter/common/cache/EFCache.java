package com.dmf.bootstarter.common.cache;

import org.springframework.cache.Cache;

/**
 * @author dmf
 * @date 2019/12/2
 */
public interface  EFCache<V> extends Cache{


    void remove(Object o);


}
