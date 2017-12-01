package com.smart.algorithm.strSearch;

/**
 * 计算最大子序列之和
 * Created by fc.w on 2017/10/16.
 */
public class MaxSubSequence {

    /**
     * 动态规划求最大子序列和:
     * 只要前i项的和还没有小于0那么子序列就一直向后扩展，否则丢弃之前的子序列开始新的子序列，同时我们要记下各个子序列的和，最后找到和最大的子序列
     * @param arr
     * @return
     */
    int maxSum(int[] arr) {
        int maxSum = Integer.MIN_VALUE;
        int sum = 0;
        /* curstart记录每次当前起始位置 */
        int start = 0, end = 0, curStart = 0;
        for (int i = 0; i < arr.length; i++) {
            if (sum < 0) {
                sum = arr[i];
                /* 记录当前的起始位置 */
                curStart = i;
            } else {
                sum += arr[i];
            }

            if (sum > maxSum) {
                maxSum = sum;
                /* 记录并更新最大子数组起始位置 */
                start = curStart;
                end = i;
            }
        }
        System.out.println(start +"    "+ end);
        return maxSum;
    }

    /**
     * 分治法求解最大子序列和
     * 这道题里面：最大子序列和可能出现在三个地方，
     * 1）整个出现在输入数据的左半部分，
     * 2）整个出现在输入数据的右半部分，
     * 3）或者跨越输入数据的中部从而占据左右两个半部分。
     * @param arr
     * @param left
     * @param right
     * @return
     */
    int maxSum_divide(int[] arr, int left, int right) {
        if (left == right) {
            if (arr[left] > 0)
                return arr[left];
            else
                return 0;
        }

        int center = (left + right) / 2;
        // 整个出现在输入数据的左半部分，
        int maxLeftSum = maxSum_divide(arr, left, center);
        // 整个出现在输入数据的右半部分
        int maxRightSum = maxSum_divide(arr, center + 1, right);

        /* 跨越输入数据的中部从而占据左右两个半部分 */
        int maxLeftBorderSum = 0, leftBorderSum = 0;
        for (int i = center; i >= left; i--) {
            leftBorderSum += arr[i];
            if (leftBorderSum > maxLeftBorderSum) {
                maxLeftBorderSum = leftBorderSum;
            }
        }

        int maxRightBorderSum = 0, rightBorderSum = 0;
        for (int i = center + 1; i <= right; i++) {
            rightBorderSum += arr[i];
            if (rightBorderSum > maxRightBorderSum) {
                maxRightBorderSum = rightBorderSum;
            }
        }

        int[] seq = {maxLeftSum, maxRightSum, (maxLeftBorderSum + maxRightBorderSum)};
        return getMax(seq);
    }

    int getMax(int[] seq) {
        int max = 0;
        for (int num : seq) {
            if (num > max) {
                max = num;
            }
        }

        return max;
    }


    public static void main(String[] args) {
        int[] arr = new int[]{0,-2,3,5,-1,2};

        MaxSubSequence s = new MaxSubSequence();
        System.out.println(s.maxSum_divide(arr, 0, arr.length));
//        System.out.println(s.maxSum(arr));
    }

}
