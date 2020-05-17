package com.dmf.boot.learn.pattern.rabbit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author dmf
 * @date 2020/5/17 14:17
 */
public class Test {

    HashMap<Integer, List<Integer>> map = new HashMap<>();
    int[] vi;
    int[] res;
    int n;

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        for (int[] pair : prerequisites) {
            map.computeIfAbsent(pair[1],(k)->new ArrayList<>()).add(pair[0]);
        }

        vi = new int[numCourses];
        res = new int[numCourses];
        n = numCourses;

        for (int i = 0; i < numCourses; i++) {
            if (vi[i] == 0) {
                if (!dfs(i)) {
                    return new int[0];
                }
            }
        }

        return res;

    }

    public boolean dfs(int i) {
        vi[i] = -1;
        List<Integer> li = map.get(i);

        if (li!=null) {
            for (Integer v : li) {
                if (vi[v] < 0 || (vi[v] == 0 && !dfs(v))) {
                    return false;
                }
            }
        }

        vi[i] = 1;
        res[--n] = i;
        return true;
    }

    public static void main(String[] args) {
        int[] order = new Test().findOrder(2, new int[][]{{1, 0}});
        System.out.println(order);
    }
}
