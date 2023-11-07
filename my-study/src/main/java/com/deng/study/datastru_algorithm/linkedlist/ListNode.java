package com.deng.study.datastru_algorithm.linkedlist;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/7/31 10:41
 */
public class ListNode {
    int val;
    ListNode next;

    ListNode() {

    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public void print(){
        ListNode listNode = this;
        while(listNode != null){
            System.out.print(listNode.val + " ");
            listNode = listNode.next;
        }
        System.out.println();
    }

    public static ListNode createList(int...vals){
        if(vals.length == 0){
            return null;
        }

        ListNode head = new ListNode(vals[0]);
        ListNode prev = head;

        for (int i = 1; i < vals.length; i++) {
            ListNode curr = new ListNode(vals[i]);
            prev.next = curr;
            prev = curr;
        }

        return head;
    }

}
