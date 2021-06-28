package com.dmf.boot.learn.leetcode;

import java.util.*;

/**
 * @author dmf
 * @date 2021/6/28 22:28
 */
public class LC_851 {

    /**
     * 公交路线：routes数组表示一系列公交路线，其中routes[i]表示一条公交路线，routes[i][j]表示当前i公交路线经过的站点，
     * 现在从source站点出发，要到target站点，求最少需要乘坐公交车数量
     *
     * @param routes 公交车路线
     * @param source 起始站点
     * @param target 终点站点
     * @return 最少公交车数量，如果不能到达终点，返回-1
     */
    public int numBusesToDestination(int[][] routes, int source, int target) {
        Map<Integer, List<Integer>> siteRouteMap = new HashMap<>();
        for (int i = 0; i < routes.length; i++) {
            for (int siteId : routes[i]) {
                List<Integer> siteRouteList = siteRouteMap.getOrDefault(siteId, new ArrayList<>());
                siteRouteList.add(i);
                siteRouteMap.put(siteId, siteRouteList);
            }
        }

        // 已经走过的路线
        Set<Integer> visitedRoute = new HashSet<>();
        // 已经走过的站点
        Set<Integer> visitedSite = new HashSet<>();

        Queue<Integer> siteQueue = new LinkedList<>();
        siteQueue.add(source);
        int carRouteCount = 0;
        while (!siteQueue.isEmpty()) {
            int curLevelSites = siteQueue.size();
            for (int i = 0; i < curLevelSites; i++) {
                int siteId = siteQueue.poll();
                if (siteId == target) {
                    return carRouteCount;
                }

                List<Integer> siteRouteList = siteRouteMap.get(siteId);
                for (Integer routeId : siteRouteList) {
                    if (visitedRoute.contains(routeId)) {
                        continue;
                    }

                    visitedRoute.add(routeId);
                    for (int site : routes[routeId]) {
                        if (visitedSite.contains(site)) {
                            continue;
                        }

                        visitedSite.add(site);
                        siteQueue.add(site);
                    }
                }
            }

            carRouteCount++;
        }

        return -1;
    }

    public static void main(String[] args) {
        LC_851 lc_851 = new LC_851();
        int[][] routes = new int[][]{{1, 2, 7}, {3, 6, 7}};
        int cars = lc_851.numBusesToDestination(routes, 1, 6);
        System.out.println(cars);
    }
}
