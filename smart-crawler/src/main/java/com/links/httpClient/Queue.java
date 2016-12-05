package com.links.httpClient;

import java.util.LinkedList;

/**
 * Created by  fc.w on 2016/12/2.
 */
public class Queue {

    private LinkedList<Object> queue = new LinkedList<Object>(); // 入队列

    public void enQueue(Object t) {
        queue.addLast(t);
    }

    public Object deQueue() {
        return queue.removeFirst();
    }

    public boolean isQueueEmpty() {
        return queue.isEmpty();
    }

    public boolean contians(Object t) {
        return queue.contains(t);
    }

    public boolean empty() {
        return queue.isEmpty();
    }

}
