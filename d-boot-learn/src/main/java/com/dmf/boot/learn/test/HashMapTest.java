package com.dmf.boot.learn.test;

import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dmf
 * @date 2019/12/24
 */
public class HashMapTest {



    @Test
    public void testHashMapFastFail(){
        System.out.println("test solve HashMap fast-fail");
        Map<Integer, String> testHashMap = new HashMap<>(8);
        testHashMap.put(2000, "2000");
        testHashMap.put(3000, "3000");
        testHashMap.put(4000, "4000");
        testHashMap.put(5000, "5000");
        System.out.println(testHashMap.size());
        for (Map.Entry<Integer, String> entry : testHashMap.entrySet()) {
            int key = entry.getKey();
            System.out.println("key=" + key);
            if (key == 3000) {
                testHashMap.remove(key);
            }
        }

        System.out.println(testHashMap.size());
        for (Map.Entry<Integer, String> entry : testHashMap.entrySet()) {
            System.out.println(entry.getKey() + "-->" + entry.getValue());
        }
    }

    @Test
    public void testConcurrentHashMapFastSafe(){
        System.out.println("test solve HashMap fast-fail");
        ConcurrentHashMap<Integer, String> testHashMap = new ConcurrentHashMap<>(8);
        testHashMap.put(2000, "2000");
        testHashMap.put(3000, "3000");
        testHashMap.put(4000, "4000");
        testHashMap.put(5000, "5000");
        System.out.println(testHashMap.size());
        for (Map.Entry<Integer, String> entry : testHashMap.entrySet()) {
            int key = entry.getKey();
            System.out.println("key=" + key);
            if (key == 2000) {
                testHashMap.remove(key);
            }
        }

        System.out.println(testHashMap.size());
        for (Map.Entry<Integer, String> entry : testHashMap.entrySet()) {
            System.out.println(entry.getKey() + "-->" + entry.getValue());
        }
    }

    @Test
    public void testHashMapFastSave(){
        System.out.println("test solve HashMap fast-save");
        Map<Integer, String> testHashMap = new HashMap<>(8);
        testHashMap.put(2000, "2000");
        testHashMap.put(3000, "3000");
        testHashMap.put(4000, "4000");
        testHashMap.put(5000, "5000");
        System.out.println(testHashMap.size());
        Iterator<Map.Entry<Integer, String>> iterator = testHashMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer, String> entry = iterator.next();
            System.out.println("key=" + entry.getKey());
            if (entry.getKey() == 4000) {
                testHashMap.remove(entry.getKey());
//                iterator.remove();
            }
        }

        System.out.println(testHashMap.size());
        for (Map.Entry<Integer, String> entry : testHashMap.entrySet()) {
            System.out.println(entry.getKey() + "-->" + entry.getValue());
        }
    }

}
