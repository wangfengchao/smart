package com.dodoca.smart.stack;

/**
 * Created by Administrator on 2016/12/25.
 */
public class LinkedStack implements StackADT {

    private LinearNode top; //指向栈顶
    private int count; //记录栈的大小

    public LinkedStack() {
        top = null;
        count = 0;
    }

    public void push(Object element) {
        LinearNode node = new LinearNode(element);
        node.setNext(top);
        top = (LinearNode) element;
        count++;
    }

    public Object pop() {
        return null;
    }

    public boolean isEmpty() {
        return (size() == 0);
    }

    public int size() {
        return count;
    }

    public Object peek() {
        return null;
    }
}
