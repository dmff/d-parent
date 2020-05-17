package com.dmf.boot.learn.algorithms;


import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * 这些信号灯每秒钟变化一次，并且灯的某实可状态完全由上一秒钟灯组的状态决定，具体的规则为：
 * 如果一个信号灯左右相邻的灯都亮，或者都灭，则下一秒该灯亮。其他情况下，信号灯都灭。（特别地，比如两边的灯，他们只有一个相邻灯）
 * OFF,OFF,OFF,OFF,ON,ON,ON,OFF,OFF
 * @author dmf
 * @date 2020/3/27 0:14
 */
public class LightEncode {

    public static void main(String[] args) {
        String str = "OFF,OFF,OFF,OFF,ON,ON,ON,OFF";
        System.out.println(test1(str, 4000));
        System.out.println(test2(str, 4000));
        HashSet<String> objects = Sets.newHashSet();
        Object[] objects1 = objects.toArray();

    }

    /**
     * 思路，找到循环开始的位置和循环的结束位置
     * @param str
     * @param s
     * @return
     */
    private static String test1(String str, int s) {
        HashMap<Integer, String> map = new HashMap<>(64);
        String tmp = encode(str);
        int end;
        map.put(0, tmp);
        for (end = 1; end < s; end++) {
            tmp = getNext(tmp);
            if (map.containsValue(tmp)) {
                break;
            } else {
                map.put(end, tmp);
            }
        }


        //如果在循环之前找到环，end会比s小，否则，end会等于s
        if (end==s) {
            return decode(getNext(tmp));
        }


        //循环开始位置，循环长度等于i-start
        final String val = tmp;
        int start = map.entrySet().stream().filter(entry->entry.getValue().equals(val)).findAny().get().getKey();
        //取模需要走的位置+循环开始位置
        return decode(map.get((s-start) % (end-start) + start));
    }


    private static String test2(String str, Integer s) {
        String tmp = encode(str);
        int i = 0;
        for (i = 0; i < s; i++) {
            tmp = getNext(tmp);
        }
        return decode(tmp);
    }

    /**
     * 计算下个序列
     * @param str
     * @return
     */
    private static String getNext(String str) {
        String res = "0";
        for (int i = 1; i < 7; i++) {
            if (str.charAt(i-1) == str.charAt(i+1)) {
                res = res + "1";
            } else {
                res = res + "0";
            }
        }
        return res + "0";
    }

    private static String encode(String str) {
        return str.replaceAll("ON", "1")
                .replaceAll("OFF", "0")
                .replace(",", "");
    }

    private static String decode(String str) {
        String tmp = str.replaceAll("0", "OFF,").replaceAll("1", "ON,");
        return tmp.substring(0, tmp.length()-1);
    }

}
