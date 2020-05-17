package com.dmf.bootstarter.common.enums;

/**
 * @author dmf
 * @date 2019/9/2
 */
public interface EFEnum<K, V> {

    K getKey();

    V getValue();
}
