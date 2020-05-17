package com.dmf.boot.learn.algorithms;


import com.dmf.boot.learn.algorithms.model.ListNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmf
 * @date 2018/9/28
 */
public class ListTest {
    /**
     * 从尾到头的顺序获取List
     * 解题思路：1.采用栈先进后出的顺序，用空间换时间
     * 2.采用递归的方式,3.反转链表
     *
     * @param listNode
     * @return
     */
    public List<Integer> getListFromTailToHead(ListNode listNode) {
        List<Integer> listFromTailToHead = new ArrayList<>();
        if (listNode == null) {
            return listFromTailToHead;
        }

        listFromTailToHead(listNode, listFromTailToHead);
        return listFromTailToHead;
    }

    private void listFromTailToHead(ListNode listNode, List<Integer> listFromTailToHead) {
        if (listNode != null) {
            listFromTailToHead(listNode.next, listFromTailToHead);
            listFromTailToHead.add(listNode.val);
        }
    }

    /**
     * 反转链表
     *
     * @param listNode
     * @return
     */
    public ListNode reverseLinkList(ListNode listNode) {
        ListNode pre = null;
        while (listNode !=null){
            ListNode next = listNode.next;
            //1->null,2->1->null,....
            listNode.next = pre;
            pre = listNode;
            listNode = next;
        }

        return pre;
    }

    public ListNode findKthToTail(ListNode head,int k) {
        if(head == null || k<=0) {
            return null;
        }

        ListNode pre = head;
        ListNode last = head;
        //往前走k步
        for(int i=0;i<k-1;i++){
            //下一个节点为null,说明链表的长度不够
            if (pre.next !=null){
                pre = pre.next;
            }else {
                return null;
            }
        }

        //走到最后一个节点，最后节点的next为null即为
        while(pre.next != null){
            pre = pre.next;
            last = last.next;
        }

        return last;
    }

    public static void main(String[] args) {
        new ListTest().test();
    }

    private void test() {
        ListNode node = new ListNode(1);
        ListNode node1 = new ListNode(2);
        node.next = node1;
        ListNode node2 = new ListNode(3);
        node1.next = node2;
        //getListFromTailToHead(null).forEach(System.out::println);
        ListNode reverseLinkList = reverseLinkList(node);
        while (reverseLinkList!=null){
            System.out.println(reverseLinkList.val);
            reverseLinkList = reverseLinkList.next;
        }
    }
}
