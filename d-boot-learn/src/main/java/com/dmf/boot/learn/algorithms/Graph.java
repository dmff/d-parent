package com.dmf.boot.learn.algorithms;


import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author dengmingfeng
 * @date 2020/3/25
 */
public class Graph {

    /**
     * 顶点
     */
    private char[] vexs;
    /**
     * 矩阵
     */
    private int[][] matrix;


    public Graph(char[] vexs, char[][] edgs) {
        int len = vexs.length;
        this.vexs = new char[len];
        //初始化顶点数组
        System.arraycopy(vexs, 0, this.vexs, 0, len);

        matrix = new int[len][len];
        for (int i = 0; i < edgs.length; i++) {
            char v1 = edgs[i][0];
            char v2 = edgs[i][1];
            matrix[getPosition(v1)][getPosition(v2)] = 1;
        }
    }

    private int getPosition(char v) {
        for (int i = 0; i < vexs.length; i++) {
            if (vexs[i] == v) {
                return i;
            }
        }

        throw new RuntimeException("不存在的顶点");
    }

    public void print() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 获取第一个相邻节点
     *
     * @param i
     * @return
     */
    private int firstVertex(int i) {
        for (int j = 0; j < vexs.length; j++) {
            if (matrix[i][j] == 1) {
                return j;
            }
        }

        return -1;
    }

    /**
     * 获取下一个相邻节点
     *
     * @param i
     * @param first
     * @return
     */
    private int nextVertex(int i, int node) {
        for (int j = node + 1; j < vexs.length; j++) {
            if (matrix[i][j] == 1) {
                return j;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
        char[][] edges = new char[][]{
                {'A', 'B'},
                {'A', 'F'},
                {'B', 'G'},
                {'B', 'C'},
                {'B', 'I'},
                {'C', 'B'},
                {'C', 'I'},
                {'C', 'D'},
                {'D', 'C'},
                {'D', 'I'},
                {'D', 'G'},
                {'D', 'H'},
                {'D', 'E'},
                {'E', 'H'},
                {'E', 'F'},
                {'F', 'G'},
                {'F', 'A'},
                {'F', 'E'},
                {'G', 'H'},
                {'G', 'D'},
                {'G', 'B'},
                {'H', 'G'},
                {'H', 'D'},
                {'H', 'E'},
                {'I', 'B'},
                {'I', 'C'}
        };

        Graph graph = new Graph(vexs, edges);
        graph.print();
        graph.dfs();
        System.out.println();
        graph.bfs();

    }

    public void dfs() {
        boolean[] visited = new boolean[vexs.length];
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                dfs(i, visited);
            }
        }
    }

    private void dfs(int i, boolean[] visited) {
        visited[i] = true;
        System.out.print(vexs[i] + " ");
        int first = firstVertex(i);
        while (first > 0) {
            if (!visited[first]) {
                dfs(first, visited);
            }
            first = nextVertex(i, first);
        }
    }

    public void bfs() {
        boolean[] visited = new boolean[vexs.length];
        Queue<Integer> queue = new LinkedBlockingQueue<>();

        for (int i = 0; i < vexs.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                System.out.print(vexs[i] + " ");
                queue.add(i);
            }

            while (!queue.isEmpty()) {
                Integer cur = queue.poll();
                int first = firstVertex(cur);
                while (first >= 0) {
                    if (!visited[first]) {
                        visited[first] = true;
                        System.out.print(vexs[first] + " ");
                        queue.add(first);
                    } else {
                        first = nextVertex(cur, first);
                    }
                }
            }
        }

    }


}
