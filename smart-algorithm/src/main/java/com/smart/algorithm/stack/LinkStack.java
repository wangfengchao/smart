package com.smart.algorithm.stack;

/**
 * 栈的链式存储结构实现：
 * Created by fc.w on 2017/7/5.
 */
public class LinkStack<E> {

    /**
     * 内部类  节点
     * @param <E>
     */
    private class Node<E> {
        E e;
        Node<E> next;

        public Node() {
        }

        public Node(E e, Node<E> next) {
            this.e = e;
            this.next = next;
        }
    }

    // 栈顶节点
    private Node<E> top;
    // 当前栈大小
    private int size = 0;

    public LinkStack() {
        top = new Node();
    }

    /**
     * 入栈：让top指向新创建的元素o，新元素的next引用指向原来的栈顶元素
     * @param e
     * @return
     */
    public boolean push(E e) {
        top = new Node(e, top);
        size++;
        return true;
    }

    /**
     * 出栈
     * @return
     */
    public Node<E> pop() {
        // 获取栈顶元素
        Node<E> temp = top;
        // 让top指向原来栈顶的下一个元素
        top = top.next;
        // 释放原栈顶元素的next引用
        temp.next = null;
        size--;
        return temp;
    }

    /**
     * 查看栈顶元素
     * @return
     */
    public Node<E> peek() {
        if (empty()) {
            throw new  RuntimeException("空栈异常！");
        } else {
            return top;
        }
    }

    /**
     *
     * @return
     */
    public boolean empty() {
        return size == 0;
    }

}
