package com.smart.algorithm.leetcode.tree;

/**
 *题意：判断一个二叉树是否为二分查找树。

 何为二分查找树？1) 左子树的值都比根节点小；2) 右子树的值都比根节点大；3) 左右子树也必须满足上面两个条件。

 需要注意的是，左子树的所有节点都要比根节点小，而非只是其左孩子比其小，右子树同样。这是很容易出错的一点是，很多人往往只考虑了每个根节点比其左孩子大比其右孩子小。
 如下面非二分查找树，如果只比较节点和其左右孩子的关系大小，它是满足的。

    5
 /     \
 4      10
      /      \
     3        11
 *
 * 正确解法：中序遍历法
 * Created by fc.w on 2017/8/15.
 */
public class ValidateBinarySearchTree_98 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    // 在中序遍历中保留上一次的值
    TreeNode preNode;

    public boolean isValidBST(TreeNode root) {
        if (root != null) {
            // 1. 先从左树开始遍历
            if (! isValidBST(root.left)) return false;
            // 2. 比较 当前值与小于上一次的值
            if (preNode != null && root.val <= preNode.val) return false;
            // 3. 赋值
            preNode = root;
            // 4. 遍历右子树
            return isValidBST(root.right);
        }

        return true;
    }

}
