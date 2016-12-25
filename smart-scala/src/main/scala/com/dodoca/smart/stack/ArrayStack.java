package com.dodoca.smart.stack;

/**
 * 数组实现栈
 * Created by Administrator on 2016/12/25.
 */
public class ArrayStack implements StackADT {

    private Object[] contents;
    private int top; //top标记下一个入栈位置，同时也表示栈的长度
    private static int SIZE = 10;

    public ArrayStack() {
        contents = new Object[SIZE];
        top = 0;
    }

    //借助于申请一个辅助空间，每次扩容一倍
    public void expand() {
        Object[] lager = new Object[size() * 2];
        for (int i = 0; i < contents.length; i++) {
            lager[i] = contents[i];
        }
        contents = lager;
    }

    public void push(Object element) {
        if (top == contents.length) {
            expand();
        }
        contents[top] = element;
        top++;
    }

    public Object pop() {
        if (isEmpty()) {
            System.out.println("stack is empty");
            System.exit(1);
        }
        top--;
        Object result = contents[top];
        contents[top] = null;
        return result;
    }

    public boolean isEmpty() {
        return (size() == 0);
    }

    public int size() {
        return top;
    }

    public Object peek() {
        Object result;
        if (isEmpty()) {
            return null;
        } else {
            result = contents[top - 1];
        }
        return result;
    }

    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack();
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
