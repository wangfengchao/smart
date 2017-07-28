package com.smart.algorithm.queue;

/**
 * Created by fc.w on 2017/7/23.
 */
public class ArrayQueue {

    private Object[] objs; // 对象数组
    private int maxSize; // 数组长度定义
    private int front; // 队头
    private int rear; // 队尾
    private int nleles; // 记录元素个数

    public ArrayQueue(int size) {
        this.maxSize = size;
        this.objs = new Object[maxSize];
        this.front = 0;
        this.rear = -1;
        this.nleles = 0;
    }

    /**
     * 入队
     * @param obj
     * @return
     */
    public void enqueue(Object obj) {
        // 处理循环
        if (rear == maxSize - 1) {
            rear = -1;
        }
        objs[++rear] = obj;
        nleles++;
    }

    /**
     * 出队
     * @return
     */
    public Object dequeue() {
        Object obj = objs[front++];
        // 处理循环
        if (front == maxSize) {
            front = 0;
        }
        nleles--;
        return obj;
    }

    /**
     * 查看元素
     * @return
     */
    public Object peek() {
        return objs[front];
    }

    /**
     * 判队列是否为空。若为空返回一个真值，否则返回一个假值。
     * @return
     */
    public boolean isEmpty() {
        return nleles == 0;
    }

    /**
     * 判队列是否已满。若已满返回一个真值，否则返回一个假值。
     * @return
     */
    public boolean isFull() {
        return nleles == maxSize;
    }

    /**
     * 返回队列长度
     * @return
     */
    public int size() {
        return nleles;
    }

    public static void main(String[] args) {
        ArrayQueue aq = new ArrayQueue(10);
        // 添加元素
        aq.enqueue(10);
        aq.enqueue(20);
        aq.enqueue(30);
        aq.enqueue(40);

        // 移除元素
        System.out.println(aq.dequeue());
        System.out.println(aq.dequeue());
        System.out.println(aq.dequeue());


    }

}
