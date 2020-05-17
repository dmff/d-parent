package com.dmf.boot.learn.collector;

import java.util.SortedMap;
import java.util.TreeMap;

/**一致性hash算法伪实现方式
 * @author dmf
 * @date 2020/3/8
 */
public class ConsistentHash {

    public static void sortArrayMap(){
        SortArrayMap sortArrayMap = new SortArrayMap();
        sortArrayMap.sort();
        sortArrayMap.firstNodeValue(1L);
    }

    public static void treeMap(){
        TreeMap treeMap = new TreeMap();
        SortedMap sortedMap = treeMap.tailMap(1L);
        if (!sortedMap.isEmpty()){
            sortedMap.get(sortedMap.firstKey());
        }else {
            treeMap.firstEntry().getValue();
        }
    }

}
