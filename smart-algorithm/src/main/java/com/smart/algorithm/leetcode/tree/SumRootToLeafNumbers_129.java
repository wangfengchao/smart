package com.smart.algorithm.leetcode.tree;

/**
 * 这是一道树的题目，一般使用递归来做，主要就是考虑递归条件和结束条件。
 * 这道题思路还是比较明确的，目标是把从根到叶子节点的所有路径得到的整数都累加起来，递归条件即是把当前的sum乘以10并且加上当前节点传入下一函数，
 * 进行递归，最终把左右子树的总和相加。结束条件的话就是如果一个节点是叶子，那么我们应该累加到结果总和中，如果节点到了空节点，则不是叶子节点，
 * 不需要加入到结果中，直接返回0即可。算法的本质是一次先序遍历，所以时间是O(n)，空间是栈大小，O(logn)
 *
 * Created by fc.w on 2017/9/28.
 */
public class SumRootToLeafNumbers_129 {

    public int sumNumbers(TreeNode root) {
        return helper(root, 0);
    }

    public int helper(TreeNode root,  int sum) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) {
            return sum * 10 + root.val;
        }

        return helper(root.left, sum * 10 + root.val) + helper(root.right, sum * 10 + root.val);
    }

    public static void main(String[] args) {
        SumRootToLeafNumbers_129 s = new SumRootToLeafNumbers_129();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        System.out.println(s.sumNumbers(root));
    }

}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}


