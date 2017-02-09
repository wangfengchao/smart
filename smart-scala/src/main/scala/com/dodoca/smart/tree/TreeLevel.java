package com.dodoca.smart.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by  fc.w on 2017/1/20.
 */
public class TreeLevel {

    /**
     * BFS
     *
     * Binary Tree Level Order Traversal
     *
     * @param root
     * @return
     */
    public static List<List<Integer>> levelOrder(BinTreeTraverse.Node root) {
        List<List<Integer>> results = new ArrayList<List<Integer>>();

        if (root == null) return results;

        Queue<BinTreeTraverse.Node> queue = new LinkedList<BinTreeTraverse.Node>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<Integer>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                BinTreeTraverse.Node head = queue.poll();
                level.add(head.data);
                if (head.leftNode != null) {
                    queue.add(head.leftNode);
                }
                if (head.rightNode != null) {
                    queue.add(head.rightNode);
                }
            }
            results.add(level);
        }
        return results;
    }

    /**
     * DFS
     * @param root
     * @return
     */
    public static ArrayList<ArrayList<Integer>> levelOrderDFS(BinTreeTraverse.Node root) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

        if (root == null) return result;

        int maxLevel = 0;
        while (true) {
            ArrayList<Integer> level = new ArrayList<Integer>();
            dfs(root, level, 0, maxLevel);
            if (level.size() == 0) break;
            result.add(level);
            maxLevel++;
        }

        return result;
    }

    private static void dfs(BinTreeTraverse.Node root, ArrayList<Integer> level, int currLevel, int maxLevel) {
        if (root == null || currLevel > maxLevel) return;

        if (currLevel == maxLevel) {
            level.add(root.data);
            return;
        }

        dfs(root.leftNode, level, currLevel + 1, maxLevel);
        dfs(root.rightNode, level, currLevel + 1, maxLevel);
    }

    public static void reverseList(ArrayList<ArrayList<Integer>> totalList) {
        if (totalList == null || totalList.size() == 1) return;
        int i = 0;
        int j = totalList.size() - 1;
        while (i < j) {
            ArrayList<Integer> tmp = totalList.get(i);
            totalList.set(i, totalList.get(j));
            totalList.set(j, tmp);
            i++;
            j--;
        }

    }
}
