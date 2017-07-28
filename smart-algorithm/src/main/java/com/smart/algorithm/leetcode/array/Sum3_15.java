package com.smart.algorithm.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fc.w on 2017/7/27.
 */
public class Sum3_15 {

    /**
     * 双指针法
     *
     * 复杂度
     时间 O(N^2) 空间 O(1)

     思路
     3Sum其实可以转化成一个2Sum的题，我们先从数组中选一个数，并将目标数减去这个数，得到一个新目标数。然后再在剩下的数中找一对和是这个新目标数的数，
     其实就转化为2Sum了。为了避免得到重复结果，我们不仅要跳过重复元素，而且要保证2Sum找的范围要是在我们最先选定的那个数之后的。
     */
    public List<List<Integer>> threeSum(int[] num) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        // 1. 数组排序
        Arrays.sort(num);
        // 2. 数组长度小于3则返回
        if (num.length < 3) return result;

        for (int i = 0; i < num.length - 2; i++) {
            // 3. 元素判重
            if (i > 0 && num[i] == num[i - 1]) continue;
            // 4. twoSum算法
            List<List<Integer>> ele = twoSum(num, i, 0 - num[i]);
            result.addAll(ele);
        }

        return result;
    }

    /**
     * Two Sum 算法
     * @param nums
     * @param i
     * @param target
     * @return
     */
    public List<List<Integer>> twoSum(int[] nums, int i, int target) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        int left = i + 1;
        int right = nums.length - 1;

        while (left < right) {
            // 1. 判断两个元素相加是否等于 target
            if (nums[left] + nums[right] == target) {
                List<Integer> re = new ArrayList<Integer>();
                re.add(nums[i]);
                re.add(nums[left]);
                re.add(nums[right]);

                result.add(re);

                // 2. 元素判重
                do {
                    left++;
                } while (left < nums.length && nums[left] == nums[left - 1]);
                do {
                    right--;
                } while (right >=0 && nums[right] == nums[right + 1]);

            } else if (nums[left] + nums[right] > target) {
                    right--;
            } else {
                left++;
            }
        }

        return result;
    }


    public static void main(String[] args) {
        int[] arr = new int[]{-1, 0, 1, 2, -1, -4};
        Sum3_15 sum = new Sum3_15();
        List<List<Integer>> result = sum.threeSum(arr);
        for (List<Integer> ele : result) {
            for (int a : ele) {
                System.out.print(a + "\t");
            }
            System.out.println("\n");
        }
    }

}
