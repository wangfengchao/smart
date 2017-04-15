package com.smart.algorithm.stack;

/**
 * 栈 后进先出
 * Created by Administrator on 2016/12/25.
 */
public class ArraySyncStack<E> {

    //java不支持泛型数组
    private Object[] stack;
    //栈顶索引
    private int index;
    //栈初始容量大小
    private static final int INIT_SIZE = 10;

    public ArraySyncStack() {
        stack = new Object[INIT_SIZE];
        index = -1;
    }

    /**
     * 构造方法
     * @param initSize
     */
    public ArraySyncStack(int initSize) {
        if (initSize < 0) {
            throw new IllegalArgumentException();
        }
        stack = new Object[initSize];
        index = -1;
    }

    /**
     * 入栈
     * @param element
     */
    public synchronized void push(E element) {
        if (isFull()) {
            Object[] temp = stack;
            stack = new Object[2 * stack.length];
            System.arraycopy(temp, 0, stack, 0, temp.length);
        }
        stack[++index] = element;
    }

    /**
     * 出栈操作
     * @return
     */
    public synchronized E pop() {
        if (!isEmpty()) {
            E temp = peek();
            stack[index--] = null;
            return temp;
        }

        return null;
    }

    /**
     * 查看栈对象
     * @return
     */
    public E peek() {
        if (!isEmpty()) {
            return (E) stack[index];
        }
        return null;
    }

    public boolean isEmpty() {
        return (index == -1);
    }

    public int size() {
        return index + 1;
    }

    /**
     * 检查栈是否已满
     * @return true or false
     */
    public boolean isFull() {
        return (index >= stack.length - 1);
    }


    public static void main(String[] args) {
        ArraySyncStack stack = new ArraySyncStack();
        System.out.println("将0到24依次压栈，然后连续10次出栈");
        for(int i = 0;i < 25;i++)
            stack.push(i);
        for(int i = 0;i < 10;i++)
            stack.pop();
        System.out.println("栈的大小为： " + stack.size());
        System.out.println("栈为空吗？： " + stack.isEmpty());
        System.out.println("栈顶元素为： " + stack.peek());
    }
}
