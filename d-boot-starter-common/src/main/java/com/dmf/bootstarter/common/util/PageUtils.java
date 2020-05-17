package com.dmf.bootstarter.common.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author dmf
 * @date 2019/9/2
 */
public class PageUtils {

    public static <T, R> PageInfo<R> transferPage(PageInfo<T> source, Function<T, R> mapper) {
        List<R> collect = source.getList().stream().map(mapper).collect(Collectors.toList());
        PageInfo<R> target = new PageInfo<>();
        BeanUtils.copyProperties(source, target);
        target.setList(collect);
        return target;
    }

    public static <T, R> Page<R> transferPage(Page<T> source, Function<T, R> mapper) {
        List<R> collect = source.stream().map(mapper).collect(Collectors.toList());
        Page<R> target = new Page<>();
        BeanUtils.copyProperties(source, target);
        target.addAll(collect);
        return target;
    }

    public static <T, R> List<R> transferList(PageInfo<T> source, Function<T, R> mapper) {
        return source.getList().stream().map(mapper).collect(Collectors.toList());
    }

    public static <T, R> List<R> transferList(List<T> source, Function<T, R> mapper) {
        if (source == null || source.size() == 0) {
            return null;
        }
        return source.stream().map(mapper).collect(Collectors.toList());
    }
}
