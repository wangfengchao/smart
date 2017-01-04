package com.dodoca.smart.links;

/**
 * Created by Administrator on 2017/1/1.
 */
public class SinglyLinkedList {
    private class Data {
        private Object obj;
        private Data next = null;
        public Data(Object obj) {
            this.obj = obj;
        }
    }

    private Data first = null;

    public void insertFirst(Object obj) {
        Data data = new Data(obj);
        data.next = first;
        first = data;
    }

    public Object deleteFirst() throws Exception {
        if (first == null) {
            throw new Exception("empty!");
        }
        Data tmp = first;
        first = tmp.next;
        return tmp.obj;
    }

    public Object find(Object obj) throws Exception {
        if (first == null)
            throw new Exception("SinglyLinkedList is empty!");
        Data temp = first.next;
        while (first != null) {
            if (temp.obj.equals(obj)) {
                return temp.obj;
            }
            temp = first.next;
        }
        return null;
    }

    public void remove(Object obj) throws Exception {
        if (first == null)
            throw new Exception("SinglyLinkedList is empty!");
        if (first.obj.equals(obj)) {
            first = first.next;
        } else {
            Data pre = first;
            Data curr = first.next;
            while (curr != null) {
                if (curr.obj.equals(obj)) {
                    pre.next = curr.next;
                }
                pre = curr;
                curr = curr.next;
            }
        }
    }

}
