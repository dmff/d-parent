package com.dmf.boot.learn.algorithms;

/**
 * @author dengmingfeng
 * @date 2020/4/18
 */
public class TrieTree {


    public static void createTrieTree(TreeNode node,String str){
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int index = chars[i] - 'a';
            if (node.childs[index] == null){
                node.childs[index] = new TreeNode();
                node.childs[index].data = chars[i];
            }

            node = node.childs[index];
        }

        node.isEnd = true;
    }

    public static boolean find(TreeNode node,String str){
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int index = chars[i] - 'a';
            if (node.childs[index] !=null){
                node = node.childs[index];
            }else {
                return false;
            }
        }

        return node.isEnd;
    }


    public static class TreeNode {
        private static final int MAX_SIZE = 26;
        private char data;
        private boolean isEnd = true;
        private TreeNode[] childs;
    }
}
