package com.smart.algorithm.leetcode.stack;

import java.util.Stack;

/**
 * 思路：
 （1）题意为用栈来实现队列。
 （2）要用栈来实现队列，首先需要了解栈和队列的性质。栈：先进后出，只能在栈顶增加和删除元素；队列：先进先出，只能在队尾增加元素，从队头删除元素。
 这样，用栈实现队列，就需要对两个栈进行操作，这里需要指定其中一个栈为存储元素的栈，假定为stack2，另一个为stack1。当有元素加入时，
 首先判断stack2是否为空（可以认为stack2是目标队列存放元素的实体），如果不为空，则需要将stack2中的元素全部放入(辅助栈)stack1中，
 这样stack1中存储的第一个元素为队尾元素；然后，将待加入队列的元素加入到stack1中，这样相当于实现了将入队的元素放入队尾；
 最后，将stack1中的元素全部放入stack2中，这样stack2的栈顶元素就变为队列第一个元素，对队列的pop和peek的操作就可以直接通过对stack2进行操作即可。
 * Created by fc.w on 2017/7/24.
 */
public class ImplementQueueUsingStacks_232 {

    private Stack<Integer> pushStack, popStack;
    public ImplementQueueUsingStacks_232() {
        pushStack = new Stack<Integer>();
        popStack = new Stack<Integer>();
    }

    public void push(int x) {
        pushStack.push(x);
    }

    public int pop() {
        if (popStack.isEmpty()) {
            while (!pushStack.isEmpty()) {
                popStack.push(pushStack.pop());
            }
        }
        return popStack.pop();
    }

    public int peek() {
        if (popStack.isEmpty()) {
            while (!pushStack.isEmpty()) {
                popStack.push(pushStack.pop());
            }
        }
        return popStack.peek();
    }

    public boolean empty() {
        return popStack.isEmpty() && pushStack.isEmpty();
    }

}
