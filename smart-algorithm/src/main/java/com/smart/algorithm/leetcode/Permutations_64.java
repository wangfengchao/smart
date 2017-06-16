package com.smart.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fc.w on 2017/5/23.
 */
public class Permutations_64 {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3};
        List<List<Integer>> res = permute(nums);
        for (List<Integer> lists : res) {
            System.out.println(lists);
        }
    }

    private static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        dfs(res, nums, 0);

        return res;
    }


    private static void dfs(List<List<Integer>> res, int[] nums, int index) {
        if (index >= nums.length) {
            List<Integer> temp = new ArrayList<Integer>();
            for (int num : nums) temp.add(num);
            res.add(temp);
        }

        for (int j = index; j < nums.length; j++) {
            swap(nums, j, index);
            dfs(res, nums, index + 1);
            swap(nums, j, index);
        }

    }


    private static void swap(int[] nums, int i, int index) {
        int temp = nums[i];
        nums[i] = nums[index];
        nums[index] = temp;
    }




}
