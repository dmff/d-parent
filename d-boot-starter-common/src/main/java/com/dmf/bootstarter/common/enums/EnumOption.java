package com.dmf.bootstarter.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnumOption {

    private String key;
    private String value;
    private Object extend;

    public EnumOption(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static <T> List<EnumOption> options(List<T> list, Function<? super T, ? extends EnumOption> mapper) {
        return list.stream().map(mapper).collect(Collectors.toList());
    }

}
