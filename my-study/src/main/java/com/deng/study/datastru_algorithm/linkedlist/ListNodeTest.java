package com.deng.study.datastru_algorithm.linkedlist;

import org.junit.Test;

import java.util.*;

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
        fourth.next = second;

        //
        ListNode listNode = checkPasCycle1(first);
        System.out.println("flag1：" + Objects.nonNull(listNode));
        if(listNode != null){
            System.out.println("listNode.val：" + listNode.val);
        }

        boolean flag2 = checkPasCycle2(first);
        System.out.println("flag2：" + flag2);

        ListNode listNode1 = detectCycle(first);
        if(listNode1 != null){
            System.out.println("listNode.val：" + listNode1.val);
        }
    }

    /**
     * 判断链表是否存在环路。
     * 第一种办法，借助于集合set，这种办法也可以判断环路的入口点
     *   时间复杂度：O(N)，最坏的情况下需要遍历每个节点一次
     *   空间复杂度：O(N)，最坏的情况下需要把每个节点插入到哈希表中一次
     *
     * @param head 头节点
     */
    private ListNode checkPasCycle1(ListNode head){
        Set<ListNode> set = new HashSet<>();
        ListNode currentNode = head;
        while(currentNode != null){
            // 直接add，看是否成功。这种办法比先判断contains再add效率要高
            if(!set.add(currentNode)){
                return currentNode;
            }
//            if(set.contains(currentNode)){
//                System.out.println(currentNode.val);
//                flag = true;
//                break;
//            }else{
//                set.add(currentNode);
//            }
            currentNode = currentNode.next;
        }
        return null;
    }

    /**
     * 判断链表是否存在环路
     * 第二种办法，使用快慢指针
     *  时间复杂度：O(N)
     *      当链表中不存在环时，快指针将先于慢指针达到链表尾部，链表中的每个节点至多被访问两次
     *      当链表中存在环时，每一轮移动后，快慢指针的距离将减少一。而初始距离为环的长度，因此至多移动N轮
     *  空间复杂度：O(1) 我们只使用了两个指针的额外空间
     * @param head 头节点
     */
    private boolean checkPasCycle2(ListNode head){
        if(head == null || head.next == null){
            return false;
        }
        ListNode slow = head;
        ListNode fast = head;
        do{
            if(fast == null || fast.next == null){
               return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }while (slow != fast);

        return true;
    }

    /**
     * 发现链表中环的位置
     *
     * 使用快慢指针，快指针一次走两步，慢指针一次走一步
     *
     * 假设链表中环外部分的长度为a，慢指针进去环后，走了b的长度与快指针相遇，剩余未走环的长度为c，则b+c=一圈
     * 此时，快指针走的长度为 a+n(b+c)+b = a+(n+1)b+nc
     *      慢指针走的长度为 2(a+b)
     *      所以两个关联，则 a+(n+1)b+nc = 2(a+b) ===> a=c+(n-1)(b+c)
     * 从a=c+(n-1)(b+c)这个公式来看，从相遇点到入环点的距离加上n-1圈的环长，恰好等于从链表到入环点的距离a
     *
     * 由于使用快慢指针，慢指针走一圈，快指针可以走两圈，所以一定会在慢指针走第一圈的时候相遇
     *
     * @param head
     * @return
     */
    private ListNode detectCycle(ListNode head){
        if(head == null){
            return null;
        }
        // 使用两个指针，起始都位于链表的头部
        ListNode slow = head,fast = head;
        while(fast != null){
            slow = slow.next;
            if(fast.next != null){
                fast = fast.next.next;
            }else{
                return null;
            }
            // 快慢指针相遇
            if(slow == fast){
                // pos指向链表头部
                ListNode pos = head;
                // 最后会在入环点相遇
                while(pos != slow){
                    pos = pos.next;
                    slow = slow.next;
                }
                return pos;
            }
        }
        return null;
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


    /**
     * 判断链表是否为回文链表
     */
    @Test
    public void isPalindrome(){
        ListNode first = new ListNode(1);
        ListNode second = new ListNode(2);
        ListNode third = new ListNode(3);
        ListNode four = new ListNode(2);
        ListNode five = new ListNode(1);
//        ListNode six = new ListNode(1);

        first.next = second;
        second.next = third;
        third.next = four;
        four.next = five;
//        five.next = six;

        boolean flag = checkIsPalindromeByArray(first);
        System.out.println(flag);

        boolean flag2 = checkIsPalindromeByPointer(first);
        System.out.println(flag2);

    }

    /**
     * 通过数组的方式
     *  1、把链表依次存入到数组中
     *  2、使用两个指针，分别指向数组的头部和尾部，进行判断
     *  时间复杂度：O(N)
     *      1）把整个链表复制到数组中，O(N)
     *      2) 遍历整个数组，执行了O(N/2)次判断，即O(N)
     *      所以，整个复杂度：O(N)
     *  空间复杂度：O(N)：使用数组存放所有的链表元素
     *
     * @param head
     */
    private boolean checkIsPalindromeByArray(ListNode head){
        List<Integer> list = new ArrayList<>();
        ListNode currNode = head;
        while(currNode != null){
            list.add(currNode.val);
            currNode = currNode.next;
        }

        int begin = 0;
        int end = list.size() - 1;
        while(begin < end){
            if(!list.get(begin).equals(list.get(end))){
                return false;
            }
            begin++;
            end--;
        }
        return true;
    }

    /**
     * 1、使用快慢指针找到链表的中间位置
     * 2、对后半段进行翻转
     * 3、判断是否回文
     * 4、恢复链表
     * 5、返回结果
     * 时间复杂度：O(N)
     * 空间复杂度：O(1)
     *
     * @param head
     * @return
     */
    private boolean checkIsPalindromeByPointer(ListNode head){

        ListNode firstHalfEnd = getFirstHalfEnd(head);
        ListNode secondHalfBegin = reverseList(firstHalfEnd.next);

        ListNode p1 = head; // 指向头节点
        ListNode p2 = secondHalfBegin; // 指向翻转后链表的头节点

        boolean flag = true;
        // while()中加入flag判断条件的原因是一旦发现flag为flase，则进行短路，不再进行后续的判断
        // 这里只用判断p2是否为空的原因是因为，后半部分的长度 <= 前半部分
        while(flag && p2 != null){
            if(p1.val != p2.val){
                flag = false;
            }
            p1 = p1.next;
            p2 = p2.next;
        }

        // 还原反转后的链表
        firstHalfEnd.next = reverseList(secondHalfBegin);
        return flag;
    }

    /**
     * 使用头插法进行链表反转
     * @param head 当前链表的起始节点
     * @return
     */
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head; // 指向头节点
        while(curr != null){
            ListNode nextTemp = curr.next; // 指向当前节点的下一个节点，先保存起来
            curr.next = prev; // 当前节点的下一个节点指向prev，第一次指向的时候，刚好next为null。从第二次开始，就不再为空
            prev = curr; // 指向当前节点，永远指向当前节点，也就是链表的第一个节点
            curr = nextTemp;
        }
        return prev;
    }

    /**
     * 如果链表为偶数，则前后两个部分链表长度相同
     * 如果链表为奇数，则中间的节点应该看做前半部分
     *
     * @param head
     * @return
     */
    private ListNode getFirstHalfEnd(ListNode head){
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

}
