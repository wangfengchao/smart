package com.smart.algorithm.leetcode.linked;

/**
 * 翻译:
 合并2个已经排序的链表，并且返回一个新的链表。这个新的链表应该由前面提到的2个链表的节点所组成。

 分析：
 注意头节点的处理，和链表结束（next为null）的处理。以下代码新增了一个头指针，来把头节点的处理和普通节点的处理统一了。
 * Created by fc.w on 2017/7/28.
 */
public class MergeTwoSortedLists_21 {

     static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            this.val = x;
        }
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode currentNode = head;

        while (true) {
            if (l1 == null && l2 == null)
                break;
             else if (l1 != null && (l2 == null || l1.val < l2.val)) {
                 currentNode.next = l1;
                 l1 = l1.next;
             } else {
                 currentNode.next = l2;
                l2 = l2.next;
             }

             currentNode = currentNode.next;
        }

        return head.next;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(6);
        ListNode l2 = new ListNode(3);

        MergeTwoSortedLists_21 m = new MergeTwoSortedLists_21();
        ListNode listNode = m.mergeTwoLists(l1,l2);
        while (listNode != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }

}
