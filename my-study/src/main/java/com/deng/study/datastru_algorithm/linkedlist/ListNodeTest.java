package com.deng.study.datastru_algorithm.linkedlist;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

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

    /**
     * 判断链表中是否有环
     */
    @Test
    public void pasCycle(){
        ListNode first = new ListNode(3);
        ListNode second = new ListNode(2);
        ListNode third = new ListNode(0);
        ListNode fourth = new ListNode(1);

        first.next = second;
        second.next = third;
        third.next = fourth;
//        fourth.next = second;

        ListNode head = new ListNode(0,first);
        checkpasCycle1(head);
        checkpasCycle2(head);
    }

    /**
     * 第一种办法，借助于集合set
     *   时间复杂度：O(N)，最坏的情况下需要遍历每个节点一次
     *   空间复杂度：O(N)，最坏的情况下需要把每个节点插入到哈希表中一次
     *
     * @param head 头节点
     */
    private void checkpasCycle1(ListNode head){
        Set<ListNode> set = new HashSet<>();
        boolean flag = false;
        while(head != null){
            ListNode currentNode = head.next;
            // 直接add，看是否成功。这种办法比先判断contains再add效率要高
            if(!set.add(currentNode)){
                flag = true;
                break;
            }
//            if(set.contains(currentNode)){
//                System.out.println(currentNode.val);
//                flag = true;
//                break;
//            }else{
//                set.add(currentNode);
//            }
            head = head.next;
        }
        System.out.println(flag);
    }

    /**
     * 使用快慢指针
     *  时间复杂度：O(N)
     *      当链表中不存在环时，快指针将先于慢指针达到链表尾部，链表中的每个节点至多被访问两次
     *      当链表中存在环时，每一轮移动后，快慢指针的距离将减少一。而初始距离为环的长度，因此至多移动N轮
     *  空间复杂度：O(1) 我们只使用了两个指针的额外空间
     * @param head 头节点
     */
    private void checkpasCycle2(ListNode head){
        ListNode slow = head;
        ListNode fast = head.next;
        boolean flag = true;
        while(slow != fast){
            if(fast == null || fast.next == null){
                flag = false;
                break;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        System.out.println(flag);
    }

    /**
     * 判断两个链表是否相交
     */
    @Test
    public void getIntersectionNode() {
        ListNode firstA = new ListNode(3);
        ListNode secondA = new ListNode(2);

        ListNode third = new ListNode(1);
        ListNode four = new ListNode(4);
        ListNode five = new ListNode(5);

        firstA.next = secondA;
        secondA.next = third;
        // 构造链表A
        ListNode headA = new ListNode(0,firstA);

        ListNode firstB = new ListNode(8);
        ListNode secondB = new ListNode(9);
        ListNode thirdB = new ListNode(6);

        firstB.next = secondB;
        secondB.next = thirdB;
        thirdB.next = third;

        third.next = four;
        four.next = five;
        // 构造链表B
        ListNode headB = new ListNode(0,firstB);

        // 第一种办法：借助哈希表，具体就是Set，时间复杂度：O(m+n)，空间复杂度：O(m)

        // 第二种办法：使用双指针法
        ListNode node = getIntersectionNode(headA, headB);
        System.out.println(node == null ? "" : node.val);
    }

    /**
     * 使用双指针法：
     *  时间复杂度：O(m+n)，两个指针同时遍历两个链表，每个指针遍历两个链表各一次
     *      第一种情况：两个链表相交
 *              链表A的长度为m，链表B的长度是n。假设链表A不相交的长度为a，链表B不相交的长度为b，两个链表相交的长度为c。则a+c=m，b+c=n
     *              1）如果a=b，则两个指针同时到达两个链表相交的节点，此时返回即可
     *              2）如果a!=b，则指针pA会遍历完链表A，指针pB会遍历完链表B，两个指针不会同时到达链表的尾部。然后pA指向链表B的头节点，pB指向链表A的头节点，
     *                  然后两个指针同时移动，在pA移动了a+c+b，pB移动了b+c+a之后，两个指针会同时达到链表相交的节点，该节点也是两个指针第一次同时指向的节点，返回即可
     *      第二种情况：两个链表不相交
     *          链表A的长度为m，链表B的长度是n。
     *              1） m=n时，两个指针会同时达到链表的尾部，然后同时变成null，此时返回null
     *              2) m!=n时，由于两个链表没有相同部分，两个指针也不会同时达到链表尾部，因此两个指针都会完两个链表，在pA移动了m+n次，pB移动了n+m次之后，两个指针
     *                  会同时变成null，此时返回null
     *  空间复杂度：O(1)，是指用了两个指针
     * @param headA
     * @param headB
     * @return
     */
    private ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null){
            return null;
        }
        ListNode pA = headA, pB = headB;
        while(pA != pB){
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }


}
