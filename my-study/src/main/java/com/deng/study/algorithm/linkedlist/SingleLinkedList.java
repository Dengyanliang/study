package com.deng.study.algorithm.linkedlist;

import lombok.extern.slf4j.Slf4j;
import java.util.Stack;

/**
 * @Desc:单链表
 * @Auther: dengyanliang
 * @Date: 2021/1/23 22:11
 */
@Slf4j
public class SingleLinkedList {

    private Node head;
    int size;

    // https://blog.csdn.net/m0_37572458/article/details/78199507
    public SingleLinkedList(){
        Node headNode = new Node(); // 分配头节点
        headNode.next = null;
        head = headNode;
    }

    /**
     * 头插法
     * @param element
     * @return
     */
    public void addFirst(int element){
        Node newNode = new Node(element);
        newNode.next = head.next; // 添加第一个节点时，next域为空，刚好符合
        head.next = newNode;
        size++;
    }

    /**
     * 尾插法：需要借助一个尾指针变量来实现
     * @param element
     */
    public void addLast(int element){
        Node tail = head;     // 从头开始遍历
        while(tail.next != null){
            tail = tail.next; // 遍历到结尾
        }
        Node newNode = new Node(element);
        tail.next = newNode;
        size++;
    }

    public int size(){
        return size;
    }

    /**
     * 按索引查找节点数据  找到index前一个就是，因为是从0 开始的
     * @param index
     * @return
     */
    public Node findByIndex(int index){
        checkValid(index);
        if(index == 0){
            return head;
        }

        int j = 1;
        Node p = head.next;
        while(p != null && j < index){
            p = p.next;
            j++;
        }
        return p;
    }

    private void checkValid(int index){
        if(index < 0 || index > size){
            throw new RuntimeException("查询数据超限");
        }
    }

    /**
     * 按值查找数据
     * @param data
     * @return
     */
    public Node findByValue(int data){
        Node p = head.next;
        while(p != null && p.data != data){
            p = p.next;
        }
        return p;
    }

    /**
     * 显示节点数据
     */
    public void show(){
        Node current = head.next;
        while(current != null){
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    /**
     * 显示节点数据
     */
    public void print(SingleLinkedList singleLinkedList){
        Node current = singleLinkedList.head.next;
        while(current != null){
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    /**
     * 在第几个位置插入数据，如果超过最大位置，则插入到最后
     * @param index
     * @param element
     */
    public void insert(int index, int element){
        if(index < 1){
            throw new RuntimeException("插入位置超限");
        }
        Node p = head.next;
        int j = 1;
        while(p != null && j < index-1){
            p = p.next;
            j++;
        }
        Node newNode = new Node(element);
        newNode.next = p.next;
        p.next = newNode;
    }

    public int getLength(){
        Node p = head.next;
        int i = 0;
        while(p != null){
            i++;
            p = p.next;
        }
        return i;
    }

    public void deleteByIndex(int index){
        checkValid(index);

        // 删除第i个元素，那么需要找到i-1个元素
        Node current = head.next; // 指向第一个节点
        Node preNode = head;      // 指向头节点
        int j = 0;
        while(current != null && j < index){
            j++;
            preNode = current;   // 指向当前节点
            current = current.next; // 指向下一个节点
        }
        preNode.next = current.next;
    }


    /**
     * 删除所有元素：需要找到被删除元素的前一个节点
     * @param element
     */
    public void deleteAllByValue(int element){
        // 删除第i个元素，那么需要找到i-1个元素
        Node current = head.next; // 指向第一个节点
        Node preNode = head;      // 指向头节点
        while(current != null){
            /*** 第一种办法 begin ***/
//            while(current != null && current.data != element){
//                preNode = current;
//                current = current.next;
//            }
//
//            if(current != null){
//                preNode.next = current.next;
//                current = current.next; // 指向下一个节点
//            }
            /*** 第一种办法 end ***/

            /*** 第二种办法 begin ***/
            if(current.data == element){
                preNode.next = current.next;
                current= current.next;
            }else{
                preNode = current;
                current = current.next;
            }
            /*** 第二种办法 end ***/
        }
    }

    /**
     * 注意与deleteAllByValue的区别  这里只删除找到的第一个元素
     * @param element
     */
    public void deleteByValue(int element){
        // 删除第i个元素，那么需要找到i-1个元素
        Node current = head.next; // 指向第一个节点
        Node preNode = head;      // 指向头节点
        while(current != null && current.data != element){
            preNode = current;
            current = current.next;
        }
        if(current != null){
            preNode.next = current.next;
        }
    }

    /**
     * 删除所有的元素
     */
    public void deleteAll(){
        // 删除第i个元素，那么需要找到i-1个元素
        Node current = head.next; // 指向第一个节点
        Node preNode = head;      // 指向头节点
        while(current != null){
            preNode.next = current.next;
            current = current.next; // 指向下一个节点
        }
    }

    /**
     * 头插法：将链表分割为两部分，一部分为头节点，另一部分为剩余部分，遍历剩余部分，每次取出一个元素，使用头插法插入到头节点的后面
     */
    public void reverse(){
        Node pre = head;      // 指向头节点
        Node cur = head.next; // 指向第一个节点
        if (cur == null || cur.next == null) {
            System.out.println("链表为空或者只有一个节点不用逆置");
            return;
        }

        Node temp = null;     // 临时变量，用于存放当前节点的下一个节点，这里非常重要
        pre.next = null;      // 头节点的下一个节点设置为空，也就是尾部设置为空
        while (cur != null) {
            temp = cur.next;
            cur.next = pre.next;  // 使用头插法插入节点
            pre.next = cur;
            cur = temp;      // 让链表右移
        }
        head = pre;
    }

    /**
     * 使用栈打印
     */
    public void reversePrint(){
        Node currNode = head.next;
        Stack<Node> stack = new Stack<>();
        while(currNode != null){
            stack.push(currNode);
            currNode = currNode.next;
        }
        while(stack.size() > 0){
            System.out.print(stack.pop().data + " ");
        }
        System.out.println();
    }

    /**
     * 使用递归调用打印
     * @param
     */
    public void reversePrint2(){
        reversePrint2(head.next);
        System.out.println();
    }

    private int i = 0;
    private void reversePrint2(Node node){
        if(node.next != null){
            i++;
            if(i == (getLength() - 3 )){
                System.out.print(node.data+" ");
                return;
            }else{
                reversePrint2(node.next);
            }
        }
        System.out.print(node.data+" ");
    }


    /**
     * 删除自小元素
     * 定义两个指针
     * 第一个指针指向前一个节点
     * 第二个指针指向当前节点
     */
    public void deleteMin(){
        checkIsEmpty();

        Node p = head;            // 最小节点的前一个指针
        Node minNode = head.next; // 暂时把第一个节点作为最小的节点
        Node curNode = head.next;
        while(curNode.next != null){  // 从第二个节点开始遍历，如果第二个节点为空，那么第一个节点就是最小的
            if(curNode.next.data < minNode.data){
                 minNode = curNode.next; // 最小节点是下一个节点
                 p = curNode;   // 下一个节点的前一个节点就是当前节点，赋给p
            }
            curNode = curNode.next;
        }
        p.next = minNode.next;
    }

    /**
     * 将链表进行排序：还是将链表分割为两部分，第一部分为头节点和第一个节点，第二部分为剩余部分，将剩余部分一个一个添加到有序链表的末尾
     */
    public void sort(){
        checkIsEmptyOrOnlyOneNode();
        Node pHead = head;             // 最小节点的前一个指针，头指针
        Node minNode = head.next;      // 暂时把第一个节点作为最小的节点
        Node curNode = head.next.next; // 第二个节点

        minNode.next = null;
        Node nextNode = null;
        while(curNode != null){
            nextNode = curNode.next;  // 一定要有指向下一个节点的指针，否则会错乱
            while(minNode != null && curNode.data > minNode.data){ // 当前节点小于当前有序链表的而第一个节点，则直接使用头插法插入；否则往后循环比较
                pHead = minNode;        // 有序链表右移
                minNode = minNode.next; // 有序链表右移
            }

            pHead.next = curNode;
            curNode.next = minNode;

            pHead = head;         // 始终指向头节点
            minNode = pHead.next; // 指向有序链表第一个节点，因为第一个节点为最值

            curNode = nextNode;
        }
    }

    /**
     * 带表头节点的单链表元素值无序,删除所有在给定区间范围内的所有元素
     */
    public void deleteInteval(int a,int b){
        if(a > b){
            System.out.println("Please input a <= b");
            return;
        }
        checkIsEmpty();

        Node pre = head;
        Node curNode = head.next;
        while(curNode != null){
            if(curNode.data >= a && curNode.data <= b){
                pre.next = curNode.next;
                curNode = curNode.next;
            }else{
                pre = curNode;          // 依次右移
                curNode = curNode.next; // 依次右移
            }
        }
    }

    /**
     * 带头节点单链表，递增输出节点值并释放节点空间
     * 第一种办法：先把链表排序，然后再输出并释放空间。第一种办法已经在前面处理过了，这里不再重复
     * 第二种办法：每次从头开始遍历，直接找到比当前节点大的元素，输出并释放空间
     */
    public void sortPrint(){
        while(head.next != null){
            Node pHead = head;
            Node curNode = head.next;
            Node minNode = curNode;
            Node nextPre = curNode;
            Node nextNode = curNode.next;
            while(nextNode != null){
                if(minNode.data > nextNode.data) {
                    pHead = nextPre;
                    minNode = nextNode;
                }
                nextPre = nextNode;       // 同时右移
                nextNode = nextNode.next;
            }
            System.out.print(" " + minNode.data);
            pHead.next = minNode.next;
        }
    }

    /**
     *  将带头节点的单链表拆分成两个链表,奇数序号在一个,偶数序号在另一个,按原来相对顺序放置
     *  这里要使用尾插法来处理
     */
    public void breakIntoTwo1(){
        SingleLinkedList s1 = new SingleLinkedList();
        SingleLinkedList s2 = new SingleLinkedList();

        Node curNode = head.next;
        Node n1 = s1.head;
        Node n2 = s2.head;
        int i = 0;
        Node nextNode = null;
        while(curNode != null){
            nextNode = curNode.next;
            if(i % 2 != 0){
//            if(curNode.data % 2 != 0){
                n1.next = curNode;
                n1 = curNode;     // 把新node赋值给n1
            }else{
                n2.next = curNode;
                n2 = curNode;    // 把新node赋值给n2
            }
            i++;
            curNode = nextNode;
        }
        n1.next = null;   // 这里一定需要带上，不然会存在打印链表的最后一个节点信息
        n2.next = null;

        System.out.println("奇数链表：");
        s1.show();

        System.out.println("偶数链表：");
        s2.show();
    }

    /**
     * 将带头节点的单链表拆分成两个链表,奇数序号在一个,偶数序号在另一个,偶数序号的逆序放置
     * 这里第一个链表使用尾插法，第二个链表使用头插法
     */
    public void breakIntoTwo2(){
        SingleLinkedList s1 = new SingleLinkedList();
        SingleLinkedList s2 = new SingleLinkedList();

        Node curNode = head.next;
        Node n1 = s1.head;
        Node n2 = s2.head;
        int i = 0;
        Node nextNode = null;
        while(curNode != null){
            nextNode = curNode.next;  // 接收下一个节点
            if(i % 2 == 0){
                n1.next = curNode;
                n1 = curNode;
            }else{
                curNode.next = n2.next;
                n2.next = curNode;
            }
            i++;
            curNode = nextNode;
        }
        n1.next = null;

        System.out.println("奇数链表：");
        s1.show();

        System.out.println("偶数链表：");
        s2.show();
    }

    /**
     * 带头节点的单链表中元素递增有序,有重复元素,去重,使重复元素只含一个
     * 首先将链表进行排序，如果当前节点和下一个节点相等，那么就把下一个节点删除了
     */
    public void deleteDuplicate(){
        sort();
        System.out.println("有序链表为：");
        show();

        Node curNode = head.next;      // 暂时把第一个节点作为最小的节点
        Node nextNode = null; // 第二个节点
        while(curNode != null){
            nextNode = curNode.next; // 第二个节点
            if(nextNode != null && curNode.data == nextNode.data){
                curNode.next = nextNode.next;
            }
            curNode = curNode.next;
        }
    }


    private void checkIsEmpty(){
        if(head.next == null){
            System.out.println("链表为空");
            throw new RuntimeException("链表为空");
        }
    }

    private void checkIsEmptyOrOnlyOneNode(){
        if (head == null || head.next == null || head.next.next == null) {
            System.out.println("链表为空或者只有一个节点不用逆置");
            return;
        }
    }

    /**
     * 两个带头节点的单链表递增,合并两个单链表，组成一个新的链表，使其递减显示,利用原来单链表的节点
     * 遍历两个单链表，并比较当前节点的值，如果相等，就一起插入的新的链表中，否则，小的往后遍历，继续比较。
     * 采用头插法
     * @param list2
     */
    public void mergeSort2(SingleLinkedList list2){
        // 先排序
        sort();
        list2.sort();

        Node curNode1 = this.head.next;
        Node curNode2 = list2.head.next;

        SingleLinkedList newList = new SingleLinkedList();
        Node newNode = newList.head;
        newNode.next = null;

        Node nextNode1 = null, nextNode2 = null;

        while(curNode1 != null && curNode2 != null){
            nextNode1 = curNode1.next;
            nextNode2 = curNode2.next;

            if(curNode1.data < curNode2.data){
                curNode1.next = newNode.next;
                newNode.next = curNode1;

                curNode1 = nextNode1;
            }else{   // 这里包含了 >=，都是应该往右移  可以看下面的代码
                curNode1.next = newNode.next;
                newNode.next= curNode1;

                curNode2.next = newNode.next;
                newNode.next = curNode2;

                curNode1 = nextNode1;
                curNode2 = nextNode2;
            }


//            if(curNode1.data == curNode2.data){
//                curNode1.next = newNode.next;
//                newNode.next= curNode1;
//
//                curNode2.next = newNode.next;
//                newNode.next = curNode2;
//
//                curNode1 = nextNode1;
//                curNode2 = nextNode2;
//            }else if(curNode1.data > curNode2.data){
//                curNode2.next = newNode.next;
//                newNode.next = curNode2;
//
//                curNode2 = nextNode2;
//            }else{
//                curNode1.next = newNode.next;
//                newNode.next = curNode1;
//
//                curNode1 = nextNode1;
//            }
        }

        while(curNode1 != null){
            nextNode1 = curNode1.next;

            curNode1.next = newNode.next;
            newNode.next = curNode1;

            curNode1 = nextNode1;
        }


        while(curNode2 != null){
            nextNode2 = curNode2.next;

            curNode2.next = newNode.next;
            newNode.next = curNode2;

            curNode2 = nextNode2;
        }
        System.out.println("newNode mergeSort2 show");
        newList.show();
    }



    /**
     * 两个带头节点的单链表递增,合并两个单链表，组成一个新的链表，使其递减显示,利用原来单链表的节点
     */
    public void mergeSort(SingleLinkedList list2){ // TODO
        sort();
        list2.sort();

        Node p = this.head.next;
        Node q = list2.head.next;
        SingleLinkedList newlist = new SingleLinkedList();
        Node r = newlist.head;
        Node after = null;
        while(p != null && q != null){
            if(p.data < q.data){
                after = p.next;
                p.next = r.next;
                r.next = p;
                p = after;
            }else{
                after = q.next;
                q.next = r.next;
                r.next = q;
                q = after;
            }
            if(q == null){
                while(p != null){
                    after = p.next;
                    p.next = r.next;
                    r.next = p;
                    p = after;
                }
            }
            if(p == null){
                while(q != null){
                    after = q.next;
                    q.next = r.next;
                    r.next = q;
                    q = after;
                }
            }
        }
        System.out.println("newList:");
        print(newlist);
    }

    /**
     * 两个带头节点的单链表递增有序,求两个链表的交集并存放于其中一个表,时间复杂度O(len1*len2)
     * @param list2
     */
    public void intersect1(SingleLinkedList list2){
        Node curNode = head.next;
        Node curNode2 = list2.head.next;

        Node pre = this.head; // 该句等同于以下两句，结果一样
        SingleLinkedList newlist = new SingleLinkedList();
        Node newPre = newlist.head;

        while(curNode != null){
            while(curNode2 != null){
                if(curNode.data == curNode2.data){
                    newPre.next = curNode;
                    newPre = curNode;

                    pre.next = curNode;
                    pre = curNode;
                }
                curNode2 = curNode2.next;
            }
            curNode2 = list2.head.next;
            curNode = curNode.next;
        }
        newPre.next = null;
        pre.next = null;

        System.out.println("newList intersect1 : ");
        newlist.show();

        System.out.println("this show : ");
        show();
    }

    /**
     * 两个带头节点的单链表递增有序,求两个链表的交集并存放于其中一个表,时间复杂度O(len1+len2)
     * @param list2
     */
    public void intersect2(SingleLinkedList list2){
        System.out.println("sort():");
        sort();
        show();
        System.out.println("list2.sort():");
        list2.sort();
        list2.show();

        Node pre = this.head;
        Node curNode = head.next;
        Node curNode2 = list2.head.next;

        // 如果有Node pre = this.head;就不用下面这两句了，结果是一样的
//        SingleLinkedList newlist = new SingleLinkedList();
//        Node newPre = newlist.head;

        while(curNode != null && curNode2 != null){
            if(curNode.data == curNode2.data){
                pre.next = curNode;
                pre = curNode;

                curNode = curNode.next;
                curNode2 = curNode2.next;
            }else if(curNode.data < curNode2.data){
                curNode = curNode.next;
            }else{
                curNode2 = curNode2.next;
            }
        }
        pre.next = null;
        System.out.println("newList intersect2 : ");
    }

    /**
     * A,B两个带头节点的 单链表递增有序,求AB公共元素构成有序单链表C
     */
    public SingleLinkedList intersectC(SingleLinkedList list2){
        System.out.println("intersectC sort():");
        sort();
        System.out.println("intersectC list2.sort():");
        list2.sort();

        Node curNode = head.next;
        Node curNode2 = list2.head.next;

        // 如果有Node pre = this.head;就不用下面这两句了，结果是一样的
        SingleLinkedList newlist = new SingleLinkedList();
        Node newPre = newlist.head;

        while(curNode != null && curNode2 != null){
            if(curNode.data == curNode2.data){
                newPre.next = curNode;
                newPre = curNode;

                curNode = curNode.next;
                curNode2 = curNode2.next;
            }else if(curNode.data < curNode2.data){
                curNode = curNode.next;
            }else{
                curNode2 = curNode2.next;
            }
        }
        newPre.next = null;
        return newlist;
    }

    /**
     * 两个整数序列构成两个带头节点的单链表A,B,判断B是否是A的连续子序列
     * @param list2
     * @return
     */
    public boolean aisContainsB1(SingleLinkedList list2){

        Node curNode = this.head.next;
        Node curNode2 = list2.head.next;

        boolean flag = true;
        while(curNode != null && curNode2 != null){
            if(curNode.data != curNode2.data) { // 当前元素不相等，则node1右移，继续比较
                flag = false;
                curNode = curNode.next;
                continue;
            }
            // 相等，同时右移
            curNode = curNode.next;
            curNode2 = curNode2.next;
            if(curNode2 == null){ // 第二个链表已经遍历完了，表示相等
                return true;
            }
        }
        return flag;
    }

    /**
     * 两个整数序列构成两个带头节点的单链表A,B,判断B是否是A的连续子序列
     * @param list2
     * @return
     */
    public boolean aisContainsB2(SingleLinkedList list2){
        if(this.getLength() < list2.getLength()){
            return false;
        }
        Node curNode1 = this.head.next;
        Node curNode2 = list2.head.next;
        while(curNode1 != null && curNode2 != null){
            if(curNode1.data == curNode2.data){
                curNode1 = curNode1.next;
                curNode2 = curNode2.next;
            }else{
                curNode1 = curNode1.next;
            }
        }
        if(curNode2 == null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 删除倒数第N个节点
     * 第一种办法：常规操作，遍历  时间复杂度 n^2
     * @param n
     * @return
     */
    public void deleteNthFromEnd1(int n){
        int length = getLength(); // 遍历第一遍
        Node pre = head;
        for(int i = 0; i < length - n; i++){ // 遍历第二遍，找到被删除的前一个节点
            pre = pre.next;
        }
        pre.next = pre.next.next;
    }


    /**
     * 删除倒数第N个节点
     * 第二种办法：非常规操作，使用两个指针
     * @param n
     * @return
     */
    public void deleteNthFromEnd2(int n){
        Node pre = head;
        Node fast = head;
        for(int i = 0; i < n; i++){ // 这里要<=N，因为下面的while循环要走到fast为null 或者fast != null 改为fast.next !=  null
            if(fast == null){
                 break;
            }
            fast = fast.next;
        }
        if(fast == null){
            pre.next = pre.next.next; // 删除首元节点
            return;
        }

        while(fast.next != null){
            pre = pre.next;
            fast = fast.next;
        }
        pre.next = pre.next.next;
    }

    /**
     * 删除倒数第N个节点
     * 第三种做法：使用递归 TODO
     *
     * @param n
     */
    public void deleteNthFromEnd3(int n){
        int pos = deleteNthFromEnd3(head,n);
        if(pos == n){  // 说明删除的是头节点
            head.next = head.next.next;
        }
    }

    private int deleteNthFromEnd3(Node node,int n){
        if(node == null){
            return 0;
        }
        int pos = deleteNthFromEnd3(node.next,n) + 1;
        if(pos == n + 1){ // 获取要删除的前一个节点
            node.next = node.next.next;
        }
        return pos;
    }



//    private static Node reverseChainRecursive(Node head){
//        System.out.println("before ----"+ head);
//        if(head == null || head.next == null){
//            System.out.println("after ---" + head);
//            return head;
//        }
//        Node headOfReverseChain = reverseChainRecursive(head.next);
//        System.out.println("before "+ headOfReverseChain);
//        head.next.next = head;
//        head.next = null;
//        System.out.println("after "+ headOfReverseChain);
////        head = headOfReverseChain;
//        return headOfReverseChain;
//    }


    public static void main(String[] args) {
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        singleLinkedList.addFirst(3);
        singleLinkedList.addFirst(2);

        singleLinkedList.addLast(6);
        singleLinkedList.addLast(4);
        singleLinkedList.addLast(5);
        singleLinkedList.addLast(1);
        singleLinkedList.addLast(6);


        singleLinkedList.show();

//        int count = singleLinkedList.size();
//        System.out.println("count:"+count);
//
//        Node node = singleLinkedList.findByIndex(4);
//        System.out.println(node.data);
//
//        node = singleLinkedList.findByValue(1);
//        System.out.println(Objects.nonNull(node) ? node.data : null);
//
//        singleLinkedList.insert(3,100);
//        singleLinkedList.show();

//        singleLinkedList.deleteByIndex(3);
//        singleLinkedList.show();

//        System.out.println("删除对应的节点：3");
//        singleLinkedList.deleteAllByValue(3);
//        singleLinkedList.show();

//        System.out.println("reversePrint:");
//        singleLinkedList.reversePrint();
//
//        System.out.print("reverse: ");
//        singleLinkedList.reverse();
//        singleLinkedList.show();

//        System.out.println("reversePrint2");
//        singleLinkedList.reversePrint2();

//        System.out.println("删除最小的节点：");
//        singleLinkedList.deleteMin();
//        singleLinkedList.show();

//        reverseChainRecursive(singleLinkedList.head);
//        singleLinkedList.show();

//        System.out.println("删除所有的节点：");
//        singleLinkedList.deleteAll();
//        singleLinkedList.show();

//        System.out.println("sort:");
//        singleLinkedList.sort();
//        singleLinkedList.show();

//        System.out.println("deleteInteval:");
//        singleLinkedList.deleteInteval(2,3);
//        singleLinkedList.show();

//        System.out.println("sortPrint:");
//        singleLinkedList.sortPrint();
//        singleLinkedList.show();

//        singleLinkedList.breakIntoTwo1();

//        singleLinkedList.breakIntoTwo2();

//        singleLinkedList.deleteDuplicate();
//        singleLinkedList.show();


//        SingleLinkedList singleLinkedList2 = new SingleLinkedList();
//        singleLinkedList2.addLast(15);
//        singleLinkedList2.addLast(10);
//        singleLinkedList2.addLast(99);
//        singleLinkedList2.addLast(66);


//        singleLinkedList.mergeSort(singleLinkedList2);

//        singleLinkedList.intersect1(singleLinkedList2);

//        singleLinkedList.intersect2(singleLinkedList2);
//        singleLinkedList.show();

//        System.out.println("newList intersectC : ");
//        SingleLinkedList singleLinkedList3 = singleLinkedList.intersectC(singleLinkedList2);
//        singleLinkedList3.show();

//        System.out.println("aisContainsB1 : ");
//        boolean flag = singleLinkedList.aisContainsB1(singleLinkedList2);
//        System.out.println("flag:"+flag);
//
//        boolean flag2 = singleLinkedList.aisContainsB2(singleLinkedList2);
//        System.out.println("flag2:"+flag2);
//
//        System.out.println("mergeSort:");
//        singleLinkedList.mergeSort2(singleLinkedList2);

        singleLinkedList.deleteNthFromEnd3(2);
        singleLinkedList.show();

    }
}
