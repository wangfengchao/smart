package com.dodoca.smart.stack;

/**
 * 定义堆栈ADT
 * Created by Administrator on 2016/12/25.
 */
public interface StackADT {
    public void push(Object element);
    public Object pop();
    public boolean isEmpty();
    public int size();
    public Object peek(); //返回栈顶对象的一个引用
    public String toString();
}
