package com.smart.algorithm.tree;

import java.util.*;

/**
 * Created by  fc.w on 2017/1/13.
 */
public class BinTreeTraverse {

    private int [] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    private static List<Node> nodeList = null;

    public static class Node {
        Node leftNode;
        Node rightNode;
        int data;

        public Node (int data) {
            this.leftNode = null;
            this.rightNode = null;
            this.data = data;
        }
    }

    /**
     * 创建二叉数
     */
    public void createTree() {
        nodeList = new ArrayList<Node>();

        for (int i = 0; i < arr.length; i++) {
            nodeList.add(new Node(arr[i]));
        }

        for (int parentIndex = 0; parentIndex < arr.length / 2 - 1; parentIndex++) {
            nodeList.get(parentIndex).leftNode = nodeList.get(parentIndex * 2 + 1);
            nodeList.get(parentIndex).rightNode = nodeList.get(parentIndex * 2 + 2);
        }

        int lastParentIndex = arr.length / 2 - 1;
        nodeList.get(lastParentIndex).leftNode = nodeList.get(lastParentIndex * 2 + 1);
        if (arr.length % 2 == 1) {
            nodeList.get(lastParentIndex).rightNode = nodeList.get(lastParentIndex * 2 + 2);
        }

    }


    /**
     * Binary Tree Paths 二叉树路径
     * 递归法
     *
     * 简单的二叉树遍历，遍历的过程中记录之前的路径，一旦遍历到叶子节点便将该路径加入结果中。
     * @param root
     * @return
     */
    static List<String> paths = new ArrayList<String>();
    public static List<String> binaryTreePaths(Node root) {
        if (root != null) findPath(root, String.valueOf(root.data));
        return paths;
    }

    private static void findPath(Node root, String path) {
        if (root.leftNode == null || root.rightNode == null) paths.add(path);
        if (root.leftNode != null) findPath(root.leftNode, path + "->" + root.leftNode.data);
        if (root.rightNode != null) findPath(root.rightNode, path + "->" + root.rightNode.data);
    }

    /**
     * Balanced Binary Tree
     * 深度优先搜索
     *
     * 时间 O(N) 空间 O(h) 递归栈空间
     *
     * 非平衡的条件是有两个叶子节点的深度相差大于1，最直接的想法就是把左子树和右子树的高度都算出来，如果相差大于1则说明不是平衡的。
     * 在递归中，从叶子结点开始一层层返回高度，叶子结点是1。我们返回-1代表非平衡，返回自然数代表有效的子树高度。
     *
     * @param root
     * @return
     */
    public static boolean isBalanced(Node root) {
        if (root == null) return true;
        int left = getHight(root.leftNode);
        int right = getHight(root.rightNode);
        return (left != -1 && right != -1 && Math.abs(left - right) <= 1);
    }

    private static int getHight(Node root) {
        if (root == null) return 0;
        int left = getHight(root.leftNode);
        int right = getHight(root.rightNode);
        if (left == -1 || right == -1 || Math.abs(left - right) > 1) return -1;
        return Math.max(left, right) + 1;
    }

    /**
     * 前序遍历
     * @param node
     */
    public static void preOrderTraverse(Node node) {
        if (node == null) return;
        System.out.print(node.data + "\t");
        preOrderTraverse(node.leftNode);
        preOrderTraverse(node.rightNode);
    }

    /**
     * 中序遍历
     * @param node
     */
    public static void inOrderTraverse(Node node) {
        if (node == null) return;
        inOrderTraverse(node.leftNode);
        System.out.print(node.data + "\t");
        inOrderTraverse(node.rightNode);
    }

    /**
     * 后序遍历
     * @param node
     */
    public static void postOrderTraverse(Node node) {
        if (node == null) return;
        postOrderTraverse(node.leftNode);
        postOrderTraverse(node.rightNode);
        System.out.print(node.data + "\t");
    }

    /**
     * 树深度
     * @param node
     * @return
     */
    public static int getDepthRecursion(Node node) {
        if (node == null) return 0;
        int llen = getDepthRecursion(node.leftNode);
        int rlen = getDepthRecursion(node.rightNode);
        return Math.max(llen, rlen) + 1;
    }


    /**
     *  Minimum Depth of Binary Tree -- LeetCode
     *
     * 其实跟Maximum Depth of Binary Tree非常类似，只是这道题因为是判断最小深度，所以必须增加一个叶子的判断（因为如果一个节点如果只有左子树或者右子树，
     * 我们不能取它左右子树中小的作为深度，因为那样会是0，我们只有在叶子节点才能判断深度，而在求最大深度的时候，因为一定会取大的那个，所以不会有这个问题）。
     * 这道题同样是递归和非递归的解法，递归解法比较常规的思路，比Maximum Depth of Binary Tree多加一个左右子树的判断，
     * @param root
     * @return
     */
    public static int minDepth(Node root) {
        if (root == null) return 0;
        if (root.leftNode == null) return minDepth(root.rightNode) + 1;
        if (root.rightNode == null) return minDepth(root.leftNode) + 1;
        return Math.min(minDepth(root.leftNode), minDepth(root.rightNode)) + 1;
    }


    /**
     * 二叉树反转
     * @param node
     * @return
     */
    public static Node invertTree(Node node) {
        if (node == null) return null;
        Node tmpNode = node.leftNode;
        node.leftNode =  invertTree(node.rightNode);
        node.rightNode = invertTree(tmpNode);
        System.out.print(node.data + "\t");
        return node;
    }

    /**
     * 获取节点数
     * @param node
     * @return
     */
    public static int size(Node node) {
        if (node == null) return 0;
        int l = size(node.leftNode);
        int r = size(node.rightNode);
        return 1 + l + r;
    }

    /**
     * 前序遍历 非递归
     * @param node
     */
    public static void nonRecInOrder(Node node) {
        Stack<Node> stackNode = new Stack<Node>();
        Node d = node;
        while (d != null || stackNode.size() > 0) {
            while (d != null) {
                System.out.print(d.data + "\t");
                stackNode.push(d);
                d = d.leftNode;
            }

            while (stackNode.size() > 0) {
                d = stackNode.pop();
                d = d.rightNode;
            }
        }
    }

    public static void nonInOrder(Node root) {
        Stack<Node> stack = new Stack<Node>();
        while(root != null || !stack.isEmpty()) {
            if (root != null) {
                stack.push(root);
                root = root.leftNode;
            } else {
                root = stack.pop();
                System.out.print(root.data + "\t");
                root = root.rightNode;
            }
        }
    }

    public static void main(String[] args) {
        BinTreeTraverse bt = new BinTreeTraverse();
        bt.createTree();
        Node node = nodeList.get(0);
        System.out.println("================前序遍历==============");
        preOrderTraverse(node);
        System.out.println();
        System.out.println("================中序遍历==============");
        inOrderTraverse(node);
        System.out.println();
        System.out.println("================后序遍历==============");
        postOrderTraverse(node);
        System.out.println();
        System.out.println("================树深度================");
        System.out.println(getDepthRecursion(node));
        System.out.println("================树反转================");
        invertTree(node);
        System.out.println();
        System.out.println("节点数：" + size(node));
        System.out.println();
        System.out.println("==============前序遍历 非递归=============");
        nonRecInOrder(node);
        System.out.println();
        System.out.println("==============中序遍历 非递归=============");
        nonInOrder(node);
        System.out.println();
        System.out.println("==============判断是否是平衡树=============");
        System.out.println(isBalanced(node));
        System.out.println();
        System.out.println("==============Binary Tree Paths 二叉树路径=============");
        List<String> paths = binaryTreePaths(node);
        for (String path : paths) {
            System.out.print(path + "\t");
        }
        System.out.println("\n");
        System.out.println("==============Minimum Depth of Binary Tree=============");
        System.out.println("Minimum Depth of Binary Tree -- " + minDepth(node));
        System.out.println();
        System.out.println("==============Binary Tree Level Order Traversal=============");
        List<List<Integer>> result = TreeLevel.levelOrder(node);
        for (List<Integer> ele : result) {
            System.out.println(ele);
        }
        System.out.println();
        System.out.println("==============Binary Tree Level Order Traversal  DFS=============");
        ArrayList<ArrayList<Integer>> re = TreeLevel.levelOrderDFS(node);
        TreeLevel.reverseList(re);
        for (List<Integer> ele : re) {
            System.out.println(ele);
        }
        System.out.println("\n ================ 二叉树的层次遍历================");
        TreeLevel.levelIterator(node);

    }

}
