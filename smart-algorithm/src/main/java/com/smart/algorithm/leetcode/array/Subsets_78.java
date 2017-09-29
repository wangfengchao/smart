package com.smart.algorithm.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a set of distinct integers, nums, return all possible subsets.
     Note: The solution set must not contain duplicate subsets.
     For example,
     If nums = [1,2,3], a solution is:
         [
         [3],
         [1],
         [2],
         [1,2,3],
         [1,3],
         [2,3],
         [1,2],
         []
        ]
 *
 *
 *
 * 回溯算法|递归实现
 本解法采用回溯算法实现，回溯算法的基本形式是“递归+循环”，正因为循环中嵌套着递归，递归中包含循环，这才使得回溯比一般的递归和单纯的循环更难理解，其实我们熟悉了它的基本形式，就会觉得这样的算法难度也不是很大。原数组中的每个元素有两种状态：存在和不存在。
 ① 外层循环逐一往中间集合 temp 中加入元素 nums[i]，使这个元素处于存在状态
 ② 开始递归，递归中携带加入新元素的 temp，并且下一次循环的起始是 i 元素的下一个，因而递归中更新 i 值为 i + 1
 ③ 将这个从中间集合 temp 中移除，使该元素处于不存在状态
 * Created by fc.w on 2017/9/29.
 */
public class Subsets_78 {

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<Integer> subList = new ArrayList<Integer>();
        dfs(result, subList, nums, 0);
        return result;
    }

    public void dfs(List<List<Integer>> result, List<Integer> subList, int[] nums, int j) {
        result.add(new ArrayList<Integer>(subList));
        for (int i = j; i < nums.length; i++) {
            subList.add(nums[i]); //① 加入 nums[i]
            dfs(result, subList, nums, i + 1); //② 递归
            subList.remove(subList.size() - 1); //③ 移除 nums[i]
        }

    }

    public static void main(String[] args) {
        Subsets_78 sub = new Subsets_78();
        int[] nums = new int[]{1, 2, 3};
        List<List<Integer>> result = sub.subsets(nums);
        for (List<Integer> s : result) {
            System.out.println(s);
        }

    }

}
