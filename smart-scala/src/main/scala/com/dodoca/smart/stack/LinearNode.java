package com.dodoca.smart.stack;

/**
 * Created by Administrator on 2016/12/25.
 */
public class LinearNode {
    private Object element;
    private LinearNode next;

    public LinearNode(Object element) {
        this.element = element;
    }

    public void setNext(LinearNode top) {
        this.next = top;
    }

}
