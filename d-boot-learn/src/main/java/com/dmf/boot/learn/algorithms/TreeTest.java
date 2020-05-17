package com.dmf.boot.learn.algorithms;


import com.dmf.boot.learn.algorithms.model.TreeNode;

/**
 * @author dmf
 * @date 2018/9/28
 */
public class TreeTest {

    /**
     * 根据先序和中序数组的数重建二叉树，
     * 递归创建左子树和右子树，根据先序遍历可以获取根点街，而根据中序遍历可以获得左子树和右子树
     */
    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        return constructBinaryTree(pre, 0, pre.length - 1, in, 0, in.length - 1);
    }

    private TreeNode constructBinaryTree(int[] pre, int preStart, int preEnd, int[] in, int inStart, int inEnd) {
        //退出提交
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }

        TreeNode root = new TreeNode(pre[preStart]);
        //找到根节点的索引在中序遍历的位置
        int rootIndex = getIndex(pre[preStart], in);
        root.left = constructBinaryTree(pre, preStart+1,preStart+rootIndex-inStart,
                in, inStart, rootIndex - 1);
        root.right = constructBinaryTree(pre, preStart+rootIndex-inStart+1,preEnd,
                in, rootIndex+1, inEnd);
        return root;
    }

    private int getIndex(int i, int[] in) {
        for (int j = 0; j < in.length; j++) {
            if (in[j] == i) {
                return j;
            }
        }
        throw new RuntimeException("二叉树的先序和中序遍历不对应");
    }

    public void pre(TreeNode treeNode){
        if (treeNode!=null){
            System.out.print(treeNode.val+" ");
            pre(treeNode.left);
            pre(treeNode.right);
        }
    }

    public void in(TreeNode treeNode){
        if (treeNode!=null){
            in(treeNode.left);
            System.out.print(treeNode.val+" ");
            in(treeNode.right);
        }
    }

    public void post(TreeNode treeNode){
        if (treeNode!=null){
            post(treeNode.left);
            post(treeNode.right);
            System.out.print(treeNode.val+" ");
        }
    }

    public static void main(String[] args) {
        reConstructTest();
    }

    private static void reConstructTest() {
        int[] pre = {1,2,4,7,3,5,6,8};
        int[] in = {4,7,2,1,5,3,8,6};
        TreeNode treeNode = new TreeTest().reConstructBinaryTree(pre, in);
        System.out.println(treeNode);
        new TreeTest().pre(treeNode);
        System.out.println();
        new TreeTest().in(treeNode);
        System.out.println();
        new TreeTest().post(treeNode);
    }
}
