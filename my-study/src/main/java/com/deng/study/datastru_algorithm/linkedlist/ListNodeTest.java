package com.deng.study.datastru_algorithm.linkedlist;

import org.junit.Test;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/7/31 10:41
 */
public class ListNodeTest {


    @Test
    public void 两两交换链表中的节点(){
        ListNode first = new ListNode(1);
        ListNode second = new ListNode(2);
        ListNode third = new ListNode(3);
        ListNode fourth = new ListNode(4);

        first.next = second;
        second.next = third;
        third.next = fourth;

        ListNode head = new ListNode(0,first);
        // 临时指向头节点
        ListNode temp = head;
        // 如果下一个节点和下一个的下一个节点都不为空，则循环
        while(temp.next != null && temp.next.next != null){
            ListNode start = temp.next;
            ListNode end = temp.next.next;

            // 头节点指向第二个节点
            temp.next = end;
            // 第一个节点指向第三个节点
            start.next = end.next;
            // 第二个节点指向第一个节点
            end.next = start;

            // 临时节点指向start，因为start目前是第二个节点了
            temp = start;
        }


        ListNode pre = head.next;
        while(pre != null){
            System.out.println(pre.val);
            pre = pre.next;
        }
    }

    @Test
    public void 排序链表(){
        ListNode first = new ListNode(4);
        ListNode second = new ListNode(2);
        ListNode third = new ListNode(1);
        ListNode fourth = new ListNode(3);

        first.next = second;
        second.next = third;
        third.next = fourth;

        ListNode head = new ListNode(0,first);

        ListNode listNode = do_排序链表(head, null);
        while(listNode != null){
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }

    /**
     * 使用归并排序，把链表拆分成两个。这里需要使用两个指针，一个fast指针步长是两步，一个slow指针步长是一步
     * @param head
     * @param tail
     */
    private ListNode do_排序链表(ListNode head, ListNode tail) {
        if(head == null){
            return head;
        }
        // 为了把一个链表断开成两个
        if(head.next == tail){
            head.next = null;
            return head;
        }
        ListNode slow = head, fast = head;
        while(fast != tail){
            slow = slow.next;
            fast = fast.next;
            if(fast != tail){
                fast = fast.next;
            }
        }
        ListNode mid = slow;
        ListNode list1 = do_排序链表(head,mid);
        ListNode list2 = do_排序链表(mid,tail);
        return merge(list1,list2);
    }

    private ListNode merge(ListNode head1, ListNode head2) {
        ListNode dummyNode = new ListNode(0);
        ListNode tempNode = dummyNode;
        ListNode temp1 = head1,temp2 = head2;
        // 遍历两个链表并进行比较
        while(temp1 != null && temp2 !=null){
            if(temp1.val <= temp2.val){
                tempNode.next = temp1;
                temp1 = temp1.next;
            }else{
                tempNode.next = temp2;
                temp2 = temp2.next;
            }
            tempNode = tempNode.next;
        }
        if (temp1 != null) {
            tempNode.next = temp1;
        }
        if(temp2 != null){
            tempNode.next = temp2;
        }
        return dummyNode.next;
    }
}
