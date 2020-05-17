package com.dmf.boot.learn.test;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmf
 * @date 2018/10/8
 */
public class BloomFilterTest {


    private static int size = 1000000;
    /**
     * 布隆过滤器：使用更小的空间进行去重，如果使用HashMap,100万的数需要开辟200万的空间，
     * 使用默认的错误率，而布隆过滤器开辟的空间相当于1/5；随着精度的提升,布隆过滤器使用的大小也会提升
     */
    public static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size,0.0001);

    public static void main(String[] args) {
        for (int i = 0; i < size; i++) {
            bloomFilter.put(i);
        }

        for (int i = 0; i < size; i++) {
            if (!bloomFilter.mightContain(i)) {
                System.out.println("有坏人逃脱了");
            }
        }

        List<Integer> list = new ArrayList<>(1000);
        for (int i = size + 10000; i < size + 20000; i++) {
            //判断不能存在的数是否在bloomFilter里面
            if (bloomFilter.mightContain(i)) {
                list.add(i);
            }
        }

        System.out.println("有误伤的数量：" + list.size());
    }

}
