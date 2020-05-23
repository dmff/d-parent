package com.dmf.boot.learn.algorithms;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengmingfeng
 * @date 2020/4/18
 */

public class TrieTree {

    /**
     * 大小写都可保存
     */
    private static final int CHILDREN_LENGTH = 26 * 2;

    /**
     * 存放的最大字符串长度
     */
    private static final int MAX_CHAR_LENGTH = 16;

    private static final char UPPERCASE_STAR = 'A';

    /**
     * 小写就要 -71
     */
    private static final char LOWERCASE_STAR = 'G';

    private TreeNode root;

    public TrieTree() {
        root = new TreeNode();
        root.data = ' ';
    }

    public void insert(String str) {
        TreeNode node = root;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int index = getIndex(chars[i]);
//            int index = chars[i] - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TreeNode(chars[i]);
            }

            node = node.children[index];
        }

        node.isEnd = true;
    }

    private int getIndex(char aChar) {
        int index;
        if (Character.isUpperCase(aChar)) {
            index = aChar - UPPERCASE_STAR;
        } else {
            //小写就要 -71
            index = aChar - LOWERCASE_STAR;
        }
        return index;
    }

    private char getChar(int index,char aChar) {
        return Character.isUpperCase(aChar) ? (char) (UPPERCASE_STAR + index) : (char)(LOWERCASE_STAR + index);
    }

    /**
     * 判断某一个单词是否在trie树之中
     *
     * @param str
     * @return
     */
    public boolean search(String str) {
        TreeNode node = root;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
//            int index = chars[i] - 'a';
            int index = getIndex(chars[i]);
            if (node.children[index] != null) {
                node = node.children[index];
            } else {
                return false;
            }
        }

        // 在每个单词的末尾都有设置为true
        // 如果当前是false，那么代表未存储这个单词
        return node.isEnd;
    }

    /**
     * 判断当前的单词是否为Trie树种某个单词的前缀，注意这里的逻辑与查询是相同的，唯一不同的是
     * 匹配完前缀之后返回true
     */
    public boolean startsWith(String prefix) {
        TreeNode node = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            int index = getIndex(c);
            if (node.children[index] == null) {
                return false;
            }
            node = node.children[index];
        }
        return true;
    }

    public List<String> searchByPrefix(String key) {
        List<String> value = new ArrayList<String>();
        if ("".equals(key)) {
            return value;
        }

        char c = key.charAt(0);
        int index = getIndex(c);

        if (root.children[index] != null) {
            return query(root.children[index], value, key.substring(1), String.valueOf(c));
        }

        return value;
    }

    private List<String> query(TreeNode child, List<String> value, String key, String result) {
        if (child.isEnd && key == null) {
            value.add(result);
        }

        if (!StringUtils.isEmpty(key)) {
            char c = key.charAt(0);
            int index = getIndex(c);
            if (child.children[index] != null) {
                query(child.children[index], value, "".equals(key.substring(1)) ? null : key.substring(1), result + c);
            }
        } else {
            //适配js***，后面多余的字符串
            for (int i = 0; i < CHILDREN_LENGTH; i++) {
                if (child.children[i] == null) {
                    continue;
                }

                char temp = getChar(i, child.children[i].data);
                query(child.children[i], value, null, result + temp);
            }
        }

        return value;
    }

    public List<String> searchAll() {
        return depth(this.root, new ArrayList<>(), new ArrayList<>());
    }

    private List<String> depth(TreeNode node, ArrayList<String> list, List<Character> characters) {
        if (node.children == null || node.children.length == 0) {
            return list;
        }

        TreeNode[] children = node.children;
        for (TreeNode child : children) {
            if (child == null) {
                continue;
            }

            List<Character> newCharacters = new ArrayList<>(characters);
            newCharacters.add(child.data);
            if (child.isEnd) {
                char[] temp = new char[newCharacters.size()];
                for (int i = 0; i < newCharacters.size(); i++) {
                    temp[i] = newCharacters.get(i);
                }
                list.add(new String(temp));
            }

            depth(child, list, new ArrayList<>(newCharacters));
        }

        return list;

    }


    @Data
    public static class TreeNode {
        private char data;
        private boolean isEnd = false;
        private TreeNode[] children = new TreeNode[CHILDREN_LENGTH];

        public TreeNode(char data) {
            this.data = data;
        }

        public TreeNode() {
        }
    }

    public static void main(String[] args) {
        TrieTree trie = new TrieTree();
        /*trie.insert("flink");
        trie.insert("netty");
        trie.insert("mysql");
        trie.insert("redis");*/
        trie.insert("java");
        trie.insert("jsf");
        trie.insert("jsp");
        trie.insert("javascript");
        trie.insert("php");

/*        List<String> list = trie.searchAll();
        System.out.println(list);*/

        List<String> prefixList = trie.searchByPrefix("js");
        System.out.println(prefixList);

//        //false
//        System.out.println(trie.search("my"));
//        // false
//        System.out.println(trie.search("mongodb"));
//        // true
//        System.out.println(trie.search("redis"));
//        // true
//        System.out.println(trie.search("mysql"));
//        // true
//        System.out.println(trie.startsWith("my"));
    }
}
