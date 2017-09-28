package com.smart.algorithm.leetcode.tree;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 层次遍历二叉树，就是首先访问二叉树的第一层元素，再访问第二层，接着访问第三层，以此类推。
 * 实现的方式是，用一个先进先出的队列作为辅助数据结构，用levelList保存每一层的元素，用resultList保存所有的levelList，然后
 （1）把根节点入队列，并把一个哨兵节点入队列，哨兵节点用于标识某一层已经结束
 （2）当队列中元素个数大于1时（除哨兵节点外还有其它元素），进入循环。访问该元素，如果该元素为哨兵节点，则说明这一层已经结束了，
 并将一个哨兵节点入队，用于标识下一层结束的地方，把levelList存入resultList，并建一个新的levelList保存下一层的元素；否则，把该节点的值放进levelList，
 并把它不为null的孩子节点入队。
 （3）把levelList加入resultList。因为最后一个哨兵节点没有办法被访问到，导致保存最后一层元素的levelList没办法在循环中被添加进resultList。


 * Created by fc.w on 2017/9/28.
 */
public class BinaryTreeLevelOrderTraversal_102 {

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if (root == null) {
            return result;
        }

        List<Integer> level = new LinkedList<Integer>();
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        /* 把root节点入队，并把一个哨兵节点入队，用于标识某一层结束 */
        queue.offer(root);
        queue.offer(null);
        /* 当队列中元素个数大于1时（除哨兵节点外还有其它元素），进入循环 */
        while (queue.size() > 1) {
            TreeNode top = queue.poll();
            /* 如果该元素为哨兵节点，则说明这一层已经结束了 */
            if (top == null) {
                result.add(level);  // 把levelList存入resultList
                queue.offer(null);  // 将一个哨兵节点入队，用于标识下一层结束的地方
                level = new LinkedList<Integer>();  // 建一个新的levelList保存下一层的元素

            } else {  // 把该节点的值放进levelList，并把它不为null的孩子节点入队
                level.add(top.val);
                if (top.left != null) queue.add(top.left);
                if (top.right != null) queue.add(top.right);
            }
        }

        result.add(level);
        return result;
    }

    public static void main(String[] args) {
        //输入：
        //     3
        //    / \
        //   9  20
        //       / \
        //      15   7
        BinaryTreeLevelOrderTraversal_102 bt = new BinaryTreeLevelOrderTraversal_102();
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        TreeNode rootRight = new TreeNode(20);
        rootRight.left = new TreeNode(15);
        rootRight.right = new TreeNode(7);
        root.right = rootRight;

        /* 结果： [
                      [3],
                      [9,20],
                      [15,7]
                    ]
         */
        List<List<Integer>> result = bt.levelOrder(root);
        for (List<Integer> level : result) {
            System.out.println(level);
        }
    }

}

