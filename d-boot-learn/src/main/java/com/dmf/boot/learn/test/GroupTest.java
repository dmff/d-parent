package com.dmf.boot.learn.test;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * @author dmf
 * @date 2018/8/8
 */
public class GroupTest {

    String spilt = "/";
    List<String> list = Lists.newArrayList("/00/00","/00/01","/00","/00/00/00","/00/00/01","/00/01/00","/00/01/01");

    /**
     * 根据code排序测试,按层级排序
     */
    @Test
    public void test1(){
        /*
        /00
        /00/00
        /00/01
        /00/00/00
        /00/00/01
        /00/01/00
        /00/01/01
        */
        list.stream().sorted((a,b)->
                a.lastIndexOf(spilt)<b.lastIndexOf(spilt) ?  -1 : a.lastIndexOf(spilt)==b.lastIndexOf(spilt) ? a.compareTo(b) : 1
        ).forEach(System.out::println);
    }

    /**
     * 获取父编码获取子节点
     * 比如/00获取/00/**
     */
    @Test
    public void test2(){
        list.stream().filter(s -> s.matches("/00/00/[0-9]{1,2}")).forEach(System.out::println);
    }


}
