package com.dmf.boot.learn.collector;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author dmf
 * @date 2020/3/8
 */
public class LRUMap<K,V> extends LinkedHashMap<K,V>{

    private final Integer CACHE_SIZE;

    public LRUMap(int initialCapacity) {
        // true 表示让 LinkedHashMap 按照访问顺序来进行排序，最近访问的放在头部，最老访问的放在尾部。
        super((int) (Math.ceil(initialCapacity / 0.75) + 1),0.75f,true);
        this.CACHE_SIZE = initialCapacity;
    }


    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // 当 map 中的数据量大于指定的缓存个数的时候，就自动删除最老的数据。
        return size()>CACHE_SIZE;
    }
}
