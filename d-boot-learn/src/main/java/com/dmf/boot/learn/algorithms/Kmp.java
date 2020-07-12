package com.dmf.boot.learn.algorithms;

public class Kmp {

    /**
     * 参考：https://baijiahao.baidu.com/s?id=1659735837100760934&wfr=spider&for=pc
     *
     * @param str
     * @param pattern
     * @return
     */
    public static int kmp(String str, String pattern) {
        int[] next = getNext(pattern);
        int j = 0;
        for (int i = 0; i < str.length(); i++) {
            while (j > 0 && str.charAt(i) != pattern.charAt(j)) {
                j = next[j];
            }

            if (str.charAt(i) == pattern.charAt(j)) {
                j++;
            }

            if (j == pattern.length()) {
                return i - pattern.length() + 1;
            }
        }

        return -1;
    }


    private static int[] getNext(String pattern) {
        int[] next = new int[pattern.length()];
        int j = 0;
        for (int i = 2; i < pattern.length(); i++) {
            while (j != 0 && pattern.charAt(j) != pattern.charAt(i - 1)) {
                //回溯
                j = next[j];
            }

            if (pattern.charAt(j) == pattern.charAt(i - 1)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    public static void main(String[] args) {
        String str = "ATGTGAGCTGGTGTGTGCFAA";
        String pattern = "GTGTGCF";
        int index = kmp(str, pattern);
        System.out.println("首次出现位置：" + index);
    }
}

