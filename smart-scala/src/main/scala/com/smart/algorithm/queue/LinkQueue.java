package com.smart.algorithm.queue;

/**
 * Created by Administrator on 2017/1/5.
 */
public class LinkQueue<T> {
    private class Node {
        public T data;
        public Node next;
        public Node(){}
        public Node(T data, Node text) {
            this.data = data;
            this.next = next;
        }
    }
    // 队列头指针
    private Node front;
    // 队列尾指针
    private Node rear;
    // 队列大小
    private int size = 0;

    public LinkQueue() {
        Node n = new Node(null, null);
        n.next = null;
        front = rear = n;
    }

    /**
     * 入队
     * @param data
     */
    public void enqueue(T data) {
        Node s = new Node(data, null);
        rear.next = s;
        rear = s;
        size++;
    }

    public T dequeue() {
        if (rear == front) {
            try {
                throw new Exception("堆栈为空");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } else {
            Node p = front.next;
            T x = p.data;
            front.next = p.next;
            if (p.next == null) {
                rear = front;
            }
            p = null;
            size--;
            return x;
        }
    }

    /**
     * 队列长队
     * @return
     * @author WWX
     */
    public int size(){
        return size;
    }

    /**
     * 判断队列是否为空
     * @return
     * @author WWX
     */
    public  boolean isEmpty(){
        return  size==0;

    }
    public String toString() {
        if(isEmpty()){
            return "[]";
        }else{
            StringBuilder sb = new StringBuilder("[");
            for(Node current=front.next;current!=null;current=current.next){
                sb.append(current.data.toString() + ", ");
            }
            int len = sb.length();
            return sb.delete(len - 2, len).append("]").toString();
        }
    }

    //测试
    public static void main(String[] args) {
        LinkQueue<Integer> queue=new LinkQueue<Integer>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);
        String s = "aa";
        System.out.println(queue);
        System.out.println("出队："+queue.dequeue());
        System.out.println("队列长度="+queue.size());
        System.out.println(queue);
        System.out.println("出队："+queue.dequeue());
        System.out.println("队列长度="+queue.size());
        System.out.println(queue);
        System.out.println("出队："+queue.dequeue());
        System.out.println("队列长度="+queue.size());
        System.out.println(queue);
    }
}
