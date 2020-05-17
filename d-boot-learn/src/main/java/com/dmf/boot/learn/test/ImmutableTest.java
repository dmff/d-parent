package com.dmf.boot.learn.test;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author dmf
 * @date 2019/12/29
 */
public class ImmutableTest {


    /**
     * 不可变集合
     */
    @Test
    public void testImmutableList(){
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        System.out.println(list);

        List unmodifiableList = Collections.unmodifiableList(list);
        ImmutableList immutableList = ImmutableList.copyOf(list);

        list.add(2);
        System.out.println(unmodifiableList);
        System.out.println(immutableList);
    }
}
