package com.smart.algorithm.leetcode.stack;

import java.util.Stack;

/**
 * 这道最小栈跟原来的栈相比就是多了一个功能，可以返回该栈的最小值。
 * Created by fc.w on 2017/7/24.
 */
public class MinStack_155 {

    private Stack<Integer> stack = new Stack<Integer>();
    private int min = Integer.MAX_VALUE;

    public void push(int x) {
        if (x <= min) {
            stack.push(min);
            min = x;
        }
        stack.push(x);
    }

    public void pop() {
        if (stack.pop() == min) {
            min = stack.pop();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return min;
    }

    public static void main(String[] args) {
        MinStack_155 minStack = new MinStack_155();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin());
//        minStack.pop();
//        minStack.top();
//        minStack.getMin();
    }

}
