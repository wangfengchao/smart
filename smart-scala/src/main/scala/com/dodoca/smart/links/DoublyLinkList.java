package com.dodoca.smart.links;

/**
 * 双向链表
 * Created by Administrator on 2017/1/2.
 */
public class DoublyLinkList {

    private class Data {
        private Object obj;
        private Data left;
        private Data right;

        public Data(Object obj) {
            this.obj = obj;
        }
    }

    private Data first = null;
    private Data last = null;

    public void insertFirst(Object obj) {
        Data data = new Data(obj);
        if (first == null)
            last = data;
        else {
            data.right = first;
            first.left = data;
        }
        first = data;
    }

    public void insertLast(Object obj) {
        Data data = new Data(obj);
        if (first == null)
            first = data;
        else {
            last.right = data;
            data.left = last;
        }
        last = data;
    }

    public boolean insertAfter(Object target, Object obj) {
        Data data = new Data(obj);
        Data cur = first;
        while (cur != null) {
            if(cur.obj.equals(target)){
                data.right = cur.right;
                data.left = cur;
                if(cur == last)
                    last = data;
                else
                    cur.right.left = data;
                cur.right = data;
                return true;
            }
            cur = cur.right;
        }

        return false;
    }

    public Object deleteFirst() throws Exception {
        if (first == null)
            throw new Exception("empty!");
        Data temp = first;
        if (first.right == null) {
            first = null;
            last = null;
        } else {
            first.right.left = null;
            first = first.right;
        }
        return temp;
    }

    public Object deleteLast() throws Exception {
        if (first == null)
            throw new Exception("empty!");
        Data temp = last;
        if (first.right == null) {
            first = null;
            last = null;
        } else {
            last.left.right = null;
            last = last.left;
        }
        return temp;
    }

    public Object delete(Object obj) throws Exception{
        if(first == null)
            throw new Exception("empty!");
        Data cur = first;
        while(cur != null){
            if(cur.obj.equals(obj)){
                if(cur == last)
                    last = cur.left;
                else
                    cur.right.left = cur.left;
                if(cur == first)
                    first = cur.right;
                else
                    cur.left.right = cur.right;
                return obj;
            }
            cur = cur.right;
        }
        return null;
    }

    public void display(){
        System.out.print("first -> last : ");
        Data data = first;
        while(data != null){
            System.out.print(data.obj.toString() + " -> ");
            data = data.right;
        }
        System.out.print("\n");
    }

    public static void main(String[] args) throws Exception{
        DoublyLinkList dll = new DoublyLinkList();
        dll.insertFirst(1);
        dll.insertLast(3);
        dll.insertAfter(1, 2);
        dll.insertAfter(3, 4);
        dll.insertAfter(4, 5);
        dll.display();
        dll.deleteFirst();
        dll.display();
        dll.deleteLast();
        dll.display();
        dll.delete(3);
        dll.display();
        dll.delete(2);
        dll.display();
        dll.delete(4);
        dll.display();
    }
}
