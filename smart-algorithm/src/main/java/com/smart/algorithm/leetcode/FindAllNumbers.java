package com.smart.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  fc.w on 2017/1/13.
 */
public class FindAllNumbers {

    public static void main(String[] args) {
        int[] nu = {4,3,2,5,9,2,3,1,1};
//        List<Integer> list = findDisappearedNumbers(nu);
//        for (int i : list) {
//            System.out.print(i + "\t");
//        }
        System.out.println("=======================================");
        List<Integer> list1 = findDisappearedNumbers1(nu);
        for (int i : list1) {
            System.out.print(i + "\t");
        }
    }

    /**
     * 第一种解法：
     *     这种解法的思路路是，对于每个数字nums[i]，如果其对应的nums[nums[i] - 1]是正数，我们就赋值为其相反数，如果已经是负数了，就不变了，
     * 那么最后我们只要把留下的整数对应的位置加入结果res中即可，参见代码如下：
     * @param nums
     * @return
     */
    public static List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < nums.length; ++i) {
            int index = Math.abs(nums[i]) - 1;
            nums[index] = nums[index] > 0 ? -nums[index] : nums[index];
        }

        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] > 0) {
                list.add(i + 1);
            }
        }

        return list;
    }

    public static List<Integer> findDisappearedNumbers1(int[] nums) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != nums[nums[i] - 1]) {
                int index1 = i;
                int index2 = nums[i] - 1;
                int tmp = nums[index1];
                nums[index1] = nums[index2];
                nums[index2] = tmp;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i+1) {
                list.add(i + 1);
            }
        }

        return list;
    }

}
