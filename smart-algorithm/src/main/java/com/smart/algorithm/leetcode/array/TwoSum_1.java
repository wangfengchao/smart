package com.smart.algorithm.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个整形数组和一个整数target，返回2个元素的下标，它们满足相加的和为target。
   你可以假定每个输入，都会恰好有一个满足条件的返回结果。
 * Created by fc.w on 2017/7/25.
 */
public class TwoSum_1 {

    /**
     * 复杂度
     O(n)空间 O(nlogn)时间

     思路
     首先将原数组复制一遍，对新数组进行排序。排序后将双指针指向头部与尾部元素，进行迭代。如果双指针指向元素之和大于目标和，则将尾部指针向前移一位，
     反之则将头部指针向后移一位，直到双指针指向元素之和等于目标和，记录这两个元素的值，然后再遍历一遍旧数组，找出这两个元素的下标。
     * @param nums
     * @param target
     * @return
     */
    public ArrayList<List<Integer>> twoSum1(int[] nums, int target) {
        ArrayList<List<Integer>> result = new ArrayList<List<Integer>>();
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            // 1. 两个目标元素之和等于target
            if (nums[left] + nums[right] == target) {
                ArrayList<Integer> ele = new ArrayList<Integer>();
                ele.add(nums[left]);
                ele.add(nums[right]);
                result.add(ele);

                // 2. 跳过重复元素
                do {
                    left++;
                } while (nums[left] == nums[left - 1] && left < nums.length);
                do {
                    right++;
                } while (nums[right] == nums[right + 1] && right >= 0);
            } else if (nums[left] + nums[right] > target) {
                right--;
            } else {
                left++;
            }
        }
        return result;
    }


    /**
     * 暴力法 Brute Force
     * 复杂度
     O(1)空间 O(n^2)时间

     思路
     通过双重循环遍历数组中所有元素的两两组合，当出现符合的和时返回两个元素的下标

     注意
     内层循环要从外层循环下标加一开始，避免遍历到两个相同的元素
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        if (nums.length < 2) {
            return result;
        }

        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2, 7,11, 15};
        int target = 9;
        TwoSum_1 to = new TwoSum_1();
        // 返回2个元素的下标
        System.out.println("暴力法 Brute Force: 返回下标：");
        int[] re = to.twoSum(nums, target);
        System.out.println(re[0] +"   "+ re[1]);

        System.out.println("排序双指针法 Sorting with Two Pointers: \n 返回元素值：");
        ArrayList<List<Integer>> re1 = to.twoSum1(nums, target);
        for (List<Integer> ele : re1) {
            for (int a : ele) {
                System.out.print(a + "\t");
            }
        }
    }

}
