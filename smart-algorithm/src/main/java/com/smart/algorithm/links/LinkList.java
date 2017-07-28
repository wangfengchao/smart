package com.smart.algorithm.links;

/**
 * 1. 单链表的创建和遍历
 * 2. 获取节点个数
 * 3. 查找单链表中的倒数第k个结点
 * 4、查找单链表中的中间结点：
 * 5、合并两个有序的单链表，合并之后的链表依然有序：
 * 6、单链表的反转：【出现频率最高】
 * Created by fc.w on 2017/7/23.
 */
public class LinkList {

    /**
     * 内部类
     */
    class Node {
        int data; // 数据
        Node next; // 指针

        public Node(int data) {
            this.data = data;
        }
    }

    public Node head;
    public Node current;

    /**
     * 向链表中添加元素
     * @param data
     */
    public void add(int data) {
        if (head == null) {
            // 如果头结点为空，说明这个链表还没有创建，那就把新的结点赋给头结点
            head = new Node(data);
            current = head;
        } else {
            // 创建新的结点，放在当前节点的后面（把新的结点合链表进行关联）
            current.next = new Node(data);
            // 把链表的当前索引向后移动一位, 此步操作完成之后，current结点指向新添加的那个结点
            current = current.next;
        }
    }

    /**
     * 1. 打印
     * @param node
     */
    public void print(Node node) {
        if (node == null) return;
        current = node;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }

    /**
     * 2. 获取节点个数
     * @param head
     * @return
     */
    public int getLength(Node head) {
        if (head == null) return 0;
        int length = 0;
        Node current = head;
        while (current != null) {
            length++;
            current = current.next;
        }

        return length;
    }

    /**
     * 3. 查找单链表中的倒数第k个结点：
     * 3.1 普通思路：
       先将整个链表从头到尾遍历一次，计算出链表的长度size，得到链表的长度之后，就好办了，直接输出第(size-k)个节点就可以了
       （注意链表为空，k为0，k为1，k大于链表中节点个数时的情况
     * @param index
     * @return
     */
    public int findLastNode(int index) {
        if (head == null) return -1;

        //第一次遍历，得到链表的长度size
        current = head;
        int size = 0;
        while (current != null) {
            size++;
            current = current.next;
        }

        //第二次遍历，输出倒数第index个结点的数据
        current = head;
        for (int i = 0; i < size - index; i++) {
            current = current.next;
        }

        return current.data;
    }

    /**
     *  3.2  改进思路：（这种思路在其他题目中也有应用）
     这里需要声明两个指针：即两个结点型的变量first和second，首先让first和second都指向第一个结点，然后让second结点往后挪k-1个位置，
     此时first和second就间隔了k-1个位置，然后整体向后移动这两个节点，直到second节点走到最后一个结点的时候，此时first节点所指向的位置就是倒数第k个节点的位置。
     时间复杂度为O（n）
     * @param head
     * @param index
     */
    public int findLastNode(Node head, int index) {
        if (index == 0 || head == null) return 0;

        Node first = head;
        Node second = head;
        // 让second结点往后挪index个位置
        for (int i = 0; i < index; i++) {
            second = second.next;
        }
        // 让first和second结点整体向后移动，直到second结点为Null
        while (second != null) {
            first = first.next;
            second = second.next;
        }
        // 当second结点为空的时候，此时first指向的结点就是我们要找的结点
        return first.data;
    }

    /**
     * 4、查找单链表中的中间结点
     * 同样，面试官不允许你算出链表的长度，该怎么做呢？
     思路：
     和上面的第2节一样，也是设置两个指针first和second，只不过这里是，两个指针同时向前走，second指针每次走两步，first指针每次走一步，
     直到second指针走到最后一个结点时，此时first指针所指的结点就是中间结点。注意链表为空，链表结点个数为1和2的情况。时间复杂度为O（n）。
     *
     * @param head
     * @return
     */
    public Node findMidNode(Node head) {
        if (head == null) return null;

        Node first = head;
        Node second = head;
        // 每次移动时，让second结点移动两位，first结点移动一位
        while (second != null && second.next != null) {
            first = first.next;
            second = second.next.next;
        }
        // 直到second结点移动到null时，此时first指针指向的位置就是中间结点的位置
        return first;
    }

    /**
     * 5、合并两个有序的单链表，合并之后的链表依然有序：
     这道题经常被各公司考察。
     例如：
     链表1：
     　　1->2->3->4
     链表2：
     　　2->3->4->5
     合并后：
     　　1->2->2->3->3->4->4->5
     解题思路：
     　　挨着比较链表1和链表2。
     　　这个类似于归并排序。尤其要注意两个链表都为空、和其中一个为空的情况。只需要O (1) 的空间。时间复杂度为O (max(len1,len2))
     * @param head1
     * @param head2
     * @return
     */
    public Node mergeLinkList(Node head1, Node head2) {
        if (head1 == null && head2 == null) return null;
        if (head1 == null) return head2;
        if (head2 == null) return head1;

        Node head; // 新链表的头结点
        Node current; // current结点指向新链表

        // 一开始，我们让current结点指向head1和head2中较小的数据，得到head结点
        if (head1.data < head2.data) {
            head = head1;
            current = head1;
            head1 = head1.next;
        } else {
            head = head2;
            current = head2;
            head2 = head2.next;
        }

        while (head1 != null && head2 != null) {
            if (head1.data < head2.data) {
                current.next = head1; // 新链表中，current指针的下一个结点对应较小的那个数据
                current = current.next; // current指针下移
                head1 = head1.next;
            } else {
                current.next = head2;
                current = current.next;
                head2 = head2.next;
            }
        }

        // 合并剩余的元素
        if (head1 != null) {  //说明链表2遍历完了，是空的
            current.next = head1;
        }
        if (head2 != null) { //说明链表1遍历完了，是空的
            current.next = head2;
        }

        return head;
    }

    /**
     * 6、单链表的反转：【出现频率最高】
     例如链表：
     　　1->2->3->4
     反转之后：
     　　4->2->2->1
     思路：
     　　从头到尾遍历原链表，每遍历一个结点，将其摘下放在新链表的最前端。注意链表为空和只有一个结点的情况。时间复杂度为O（n）
     * @param head
     * @return
     */
    public Node reverseList(Node head) {
        if (head == null) return null;

        Node current = head;
        Node next = null; // 定义当前结点的下一个结点
        Node reverseHead = null; // 反转后新链表的表头

        while (current != null) {
            next = current.next;
            current.next = reverseHead;
            reverseHead = current;

            current = next;
        }

        return reverseHead;
    }


    public static void main(String[] args) {
        LinkList list = new LinkList();
        // 向LinkList中添加数据
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        // 从head节点开始遍历输出
        list.print(list.head);
        System.out.println("节点个数：" + list.getLength(list.head));

        int data = list.findLastNode(3);
        System.out.println( "3.1 查找链表中倒数第3个元素：" + data);
        int data1 = list.findLastNode(list.head, 3);
        System.out.println( "3.2 查找链表中倒数第3个元素：" + data1);
        Node node = list.findMidNode(list.head);
        System.out.println("4、查找单链表中的中间结点: " + node.data);


        //------------------------------合并两个有序的单链表  开始-----------------------------------------------
        System.out.println("5、合并两个有序的单链表，合并之后的链表依然有序：");
        LinkList list1 = new LinkList();
        LinkList list2 = new LinkList();
        // 向LinkList中添加数据
        for (int i = 0; i <= 4; i++) {
            list1.add(i);
        }

        for (int i = 3; i <= 8; i++) {
            list2.add(i);
        }

        LinkList list3 = new LinkList();
        list3.head = list3.mergeLinkList(list1.head, list2.head); //将list1和list2合并，存放到list3中

        list3.print(list3.head);// 从head节点开始遍历输出
        //------------------------------合并两个有序的单链表  结束-----------------------------------------------



        System.out.println("6、单链表的反转：【出现频率最高】");
        Node reverseList = list.reverseList(list.head);
        while (reverseList != null) {
            System.out.print(reverseList.data + "\t");
            reverseList = reverseList.next;
        }

    }

}
