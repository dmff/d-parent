package com.dmf.bootstarter.common.enums;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public  class EnumUtils {

    public static <E extends EFEnum<K, V>, K, V> List<EnumOption> list(Class<E> clazz) {
        EFEnum[] enums = clazz.getEnumConstants();
        return Arrays.stream(enums).map(e->{
            EnumOption enumOption = new EnumOption();
            enumOption.setKey(e.getKey().toString());
            enumOption.setValue(e.getValue().toString());
            return enumOption;
        }).collect(Collectors.toList());
    }

    public static <E extends EFEnum<K, V>, K, V> V getValueByKey(Class<E> clazz, K key) {
        EFEnum<K, V> e = getEnumByKey(clazz, key);
        return e == null ? null : e.getValue();
    }

    public static <E extends EFEnum<K, V>, K, V> K getKeyByValue(Class<E> clazz, V value) {
        EFEnum<K, V> e = getEnumByValue(clazz, value);
        return e == null ? null : e.getKey();
    }

    public static <E extends EFEnum<K, V>, K, V> EFEnum<K, V> getEnumByKey(Class<E> clazz, K key) {
        EFEnum<K, V>[] enums = clazz.getEnumConstants();
        return Arrays.stream(enums)
                .filter(e -> Objects.equals(key, e.getKey()))
                .findFirst().orElse(null);
    }

    public static <E extends EFEnum<K, V>, K, V> EFEnum<K, V> getEnumByValue(Class<E> clazz, V value) {
        EFEnum<K, V>[] enums = clazz.getEnumConstants();
        return Arrays.stream(enums)
                .filter(e -> Objects.equals(value, e.getValue()))
                .findFirst().orElse(null);
    }

}
