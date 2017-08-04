package com.smart.algorithm.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * 【解析】
 题意：给一些已经按开始时间排好序的区间，现在往里面插入一个区间，如果有重叠就合并区间，返回插入后的区间序列。
 思路：先插入，在合并重叠区间。既然是已经排好序的，就可以用二分查找的方法，把要插入的这个区间放到应该的位置。
 合并重叠区间的方法《【LeetCode】Merge Intervals 解题报告》一样。
 * Created by fc.w on 2017/7/31.
 */
public class InsertInterval_57 {

    public static class Interval {
        int start;
        int end;
        Interval() { start = 0; end = 0; }
        Interval(int s, int e) { start = s; end = e; }
    }

    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        List<Interval> intervalList = new ArrayList<Interval>();

        // 1. 添加区间元素
        int left = 0;
        int right = intervals.size() - 1;
        while (left <= right) {
            int mid = (left + right) >> 1;
            // 2. 如果新添加的interval start 小于 集合中的start, 则 right 向前移动mid - 1 位。 否则left向后移动mid + 1 位。
            if (intervals.get(mid).start > newInterval.start) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        // 3. 新元素添加到集合中指定位置
        intervals.add(left, newInterval);

        int start = intervals.get(0).start;
        int end = intervals.get(0).end;
        // 4. 合并重叠区间数据
        for (int i = 1; i < intervals.size(); i++) {
            Interval interval = intervals.get(i);
            if (interval.start > end) {
                intervalList.add(new Interval(start, end));
                start = interval.start;
                end = interval.end;
            } else {
                end = Math.max(end, interval.end);
            }
        }

        intervalList.add(new Interval(start, end));
        return intervalList;
    }

    public static void main(String[] args) {
        List<Interval> intervals = new ArrayList<Interval>();
//        intervals.add(new Interval(1, 2));
//        intervals.add(new Interval(3, 5));
//        intervals.add(new Interval(6, 7));
//        intervals.add(new Interval(8, 10));
//        intervals.add(new Interval(12, 16));

        Interval newInterval = new Interval(4, 9);

        InsertInterval_57 m = new InsertInterval_57();

        List<Interval> result = m.insert(intervals, newInterval);
        for (Interval i : result) {
            System.out.println(i.start +"  "+i.end);
        }
    }

}
