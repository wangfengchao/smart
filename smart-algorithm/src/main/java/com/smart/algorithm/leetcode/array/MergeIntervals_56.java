package com.smart.algorithm.leetcode.array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 【解析】
 题意：有很多个区间，把有重叠的区间合并。

 思路：先排序，然后检查相邻两个区间，看前一个区间的结尾是否大于后一个区间的开始，注意前一个区间包含后一个区间的情况。

 用Java自带的sort()方法，只要自己重写compare()方法即可。
 * Created by fc.w on 2017/7/31.
 */
public class MergeIntervals_56 {

    public static class Interval {
        int start;
        int end;
        Interval() { start = 0; end = 0; }
        Interval(int s, int e) { start = s; end = e; }
    }

    /**
     * sort方法，
     */
    public class MyComparator implements Comparator<Interval> {

        public int compare(Interval o1, Interval o2) {
            return o1.start - o2.start;
        }
    }

    public List<Interval> merge(List<Interval> intervals) {
        List<Interval> result = new ArrayList<Interval>();
        if (intervals.size() == 0) return result;

        // 区间数据排序
        Collections.sort(intervals, new MyComparator());

        // 重叠区间数据合并
        int start = intervals.get(0).start;
        int end = intervals.get(0).end;

        for (int i = 1; i < intervals.size(); i++) {
            Interval interval = intervals.get(i);
            // 判断前区间的end 是否大于 后区间的start
            if (interval.start > end) {
                result.add(new Interval(start, end));
                start = interval.start;
                end = interval.end;
            } else {
                end = Math.max(end, interval.end);
            }
        }
        result.add(new Interval(start, end));
        return result;
    }

    public static void main(String[] args) {
        List<Interval> intervals = new ArrayList<Interval>();
        intervals.add(new Interval(1, 3));
        intervals.add(new Interval(2, 6));
        intervals.add(new Interval(8, 10));
        intervals.add(new Interval(15, 18));

        MergeIntervals_56 m = new MergeIntervals_56();

        List<Interval> result = m.merge(intervals);
        for (Interval i : result) {
            System.out.println(i.start +"  "+i.end);
        }
    }

}
