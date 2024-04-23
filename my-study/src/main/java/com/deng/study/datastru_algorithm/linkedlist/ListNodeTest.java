package com.deng.study.datastru_algorithm.linkedlist;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/7/31 10:41
 */
public class ListNodeTest {

    @Test
    public void 两两交换链表中的节点(){
        ListNode first = ListNode.createList(1, 2, 3, 4);

        ListNode head = new ListNode(-1,first);
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

        ListNode newList = head.next;
        newList.print();
    }

    @Test
    public void 排序链表(){
        ListNode first = ListNode.createList(4, 2, 1, 3);
        ListNode head = new ListNode(-1,first);

        ListNode newList = do_排序链表(head, null);
        newList.print();
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
        ListNode dummyNode = new ListNode(-1);
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
        ListNode first = ListNode.createList(3, 2, 0, 1);

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
            ListNode nextNode = curr.next; // 指向当前节点的下一个节点，先保存起来
            curr.next = prev; // 当前节点的下一个节点指向prev，第一次指向的时候，刚好next为null。从第二次开始，就不再为空
            prev = curr; // 指向当前节点，永远指向当前节点，也就是链表的第一个节点
            curr = nextNode;
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

    @Test
    public void merge(){
        ListNode firstA = ListNode.createList(1, 2, 4);

        ListNode firstB = ListNode.createList(1, 3, 4);

//        ListNode newList = mergeTwoListsByAddFirst(firstA, firstB);
//        newList.print();

        ListNode newList2 = mergeTwoListsByAddLast(firstA, firstB);
        newList2.print();
    }

    /**
     * 使用头插法
     *
     * @param list1
     * @param list2
     * @return
     */
    private ListNode mergeTwoListsByAddFirst(ListNode list1, ListNode list2) {
        if(list1 == null && list2 == null){
            return null;
        }
        if(list1 == null){
            return list2;
        }
        if(list2 == null){
            return list1;
        }
        ListNode p1 = list1;
        ListNode p2 = list2;

        ListNode newList = new ListNode();
        ListNode temp = null;

        while(p1 != null && p2 != null){
            if(p1.val > p2.val){
                temp = p2.next;

                p2.next = newList.next; // 第一次运行到这里之后，此时p2.next为空
                newList.next = p2;

                p2 = temp;
            }else{
                temp = p1.next;

                p1.next = newList.next; // 第一次运行到这里之后，此时p1.next为空
                newList.next = p1;

                p1 = temp;
            }
        }
        while (p1 != null){
            temp = p1.next;

            p1.next = newList.next;
            newList.next = p1;

            p1 = temp;
        }
        while(p2 != null){
            temp = p2.next;

            p2.next = newList.next;
            newList.next = p2;

            p2 = temp;
        }

        return newList;
    }

    private ListNode mergeTwoListsByAddLast(ListNode list1, ListNode list2) {
        if(list1 == null && list2 == null){
            return null;
        }
        if(list1 == null){
            return list2;
        }
        if(list2 == null){
            return list1;
        }
        ListNode p1 = list1;
        ListNode p2 = list2;

        ListNode newList = new ListNode();
        ListNode newHead = newList;
        ListNode temp = null;

        while(p1 != null && p2 != null){
            if(p1.val < p2.val){
                temp = p1.next;

                newHead.next = p1;
                newHead = newHead.next;

                p1 = temp;
            }else{
                temp = p2.next;

                newHead.next = p2;
                newHead = newHead.next;

                p2 = temp;

            }
        }
        while (p1 != null){
            temp = p1.next;

            newHead.next = p1;
            newHead = newHead.next;

            p1 = temp;
        }
        while(p2 != null){
            temp = p2.next;

            newHead.next = p2;
            newHead = newHead.next;

            p2 = temp;
        }

        return newList;
    }

    /**
     * 两数相加：
     *
     * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
     * 请你将两个数相加，并以相同形式返回一个表示和的链表。
     * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头
     */
    @Test
    public void addTwoNumbers(){
        ListNode l11 = ListNode.createList(2, 4, 3);
        ListNode l21 = ListNode.createList(5, 6, 7);

        ListNode newList = addTwoNumbers(l11,l21);
        newList.print();
    }

    private ListNode addTwoNumbers(ListNode l1, ListNode l2) {
       ListNode head = null, tail = null;
       int carry = 0; // 进位值
       while (l1 != null || l2 != null) {
           int n1 = l1 != null ? l1.val : 0;
           int n2 = l2 != null ? l2.val : 0;
           int sum = n1 + n2 + carry; // sum 只能单次循环使用，不能定义在外面
           if(head == null){
               head = tail = new ListNode(sum % 10);
           }else{
               tail.next = new ListNode(sum % 10);
               tail = tail.next;
           }
           carry = sum / 10;
           if(l1 != null){
               l1 = l1.next;
           }
           if(l2 != null){
               l2 = l2.next;
           }
       }
       // 链表结束后，如果carry > 0，则表示是进位值，需要新构造一个链表
       if(carry > 0){
           tail.next = new ListNode(carry);
       }
       return head;

    }

    /**
     * 删除排序链表中的重复元素 II
     * 一次遍历解决
     * 时间复杂度：O(N)
     * 空间复杂度：O(1)
     */
    @Test
    public void deleteDuplicates(){
        ListNode l1 = ListNode.createList(1, 2, 3, 3, 4, 4, 5);
        l1.print();

        ListNode listNode = deleteDuplicates(l1);
        listNode.print();

    }

    private ListNode deleteDuplicates(ListNode head) {
        if(head == null){
            return null;
        }
        ListNode dummy = new ListNode(-1,head);
        ListNode curr = dummy;
        while(curr.next != null && curr.next.next != null){
            if(curr.next.val == curr.next.next.val){
                int x = curr.next.val;
                while(curr.next != null && curr.next.val == x){
                    curr.next = curr.next.next;
                }
            }else{
                curr = curr.next;
            }
        }
        return dummy.next;
    }


    /**
     * 反转链表 II
     *
     */
    @Test
    public void reverseBetween() {
        ListNode head = ListNode.createList(9, 7, 2, 5, 4, 3, 6);

        /********** 第一种办法 ************/
//        ListNode reveredList1 = reverseBetween1(head,2,5);
//        reveredList1.print();

        ListNode reveredList2 = reverseBetween2(head,2,5);
        reveredList2.print();

    }


    /**
     * 时间复杂度：O(N)
     * 空间复杂度：O(1)
     *
     * 这里如果开始节点和结束节点分别是头节点和尾节点，那么就需要遍历两次
     *
     * @param head 原始链表的头节点
     * @param left 需要翻转的左侧节点的位置
     * @param right 需要翻转的右侧节点的位置
     * @return
     */
    private ListNode reverseBetween1(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(-1,head);

        // 从虚拟节点走left-1步，走到left的前驱节点
        ListNode leftPrev = dummy;
        for (int i = 0; i < left-1; i++) {
            leftPrev = leftPrev.next;
        }

        ListNode rightNode = leftPrev;
        // 再从prev出发，再走right-left+1步，定位出right的节点
        for (int i = 0; i < right-left+1; i++) {
            rightNode = rightNode.next;
        }

        // 定义出需要反转的左右结点，进行切断
        ListNode leftNode = leftPrev.next;
        ListNode curr = rightNode.next;

        // 对待翻转的链表进行截断
        leftPrev.next = null;
        rightNode.next = null;

        // 进行翻转
        reverseList(leftNode);

        // 进行拼装
        leftPrev.next = rightNode;
        // 通过上述翻转后，leftNode已经移动到rightNode位置，所以它的next就是当前的节点
        leftNode.next = curr;

        return dummy.next;
    }

    /**
     * 第二种方法，只遍历一次，类似于冒泡算法
     * 这里需要使用三个指针变量：
     *  prev：永远指向待翻转节点的第一个节点的前驱节点
     *  curr：带翻转节点的第一个节点left
     *  next：永远指向curr的下一个节点，curr变化后，next会变化
     *
     * @return
     */
    private ListNode reverseBetween2(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(-1,head);

        ListNode prev = dummy;
        for (int i = 0; i < left - 1; i++) {
            prev = prev.next;
        }
        ListNode curr = prev.next;
        ListNode next = null;
        for (int i = 0; i < right - left; i++) {
            // 把当前节点的下一个节点保存起来
            next = curr.next;

            // curr指向下一个节点的next
            curr.next = next.next;

            // next指向前驱节点的next，这样相当于后面两个节点进行了交换
            next.next = prev.next;

            // 前驱节点指向下一个节点
            prev.next = next;
        }

        return dummy.next;

    }


    /**
     * 旋转链表：给你一个链表的头节点 head ，旋转链表，将链表每个节点向右移动 k 个位置。
     *
     */
    @Test
    public void rotateRight() {
        ListNode head = ListNode.createList(1, 2, 3, 4, 5);
        ListNode newList = rotateRight(head, 2);
        newList.print();
    }

    private ListNode rotateRight(ListNode head, int k) {
        if(k == 0 || head == null || head.next == null){
            return head;
        }
        // 找到原始链表的最后一个节点，以及链表的长度
        int n = 1; // 这里n=1是因为head节点不为空
        ListNode iter = head;
        while(iter.next != null){
            iter = iter.next;
            n++;
        }

        // 计算出需要移动多少个位置
        int add = n - k % n;
        if(add == n){ // 说明不需要移动
            return head;
        }
        // 把原始链表首尾连接起来，构成一个环路
        iter.next = head;
        while (add-- > 0){
            iter = iter.next;
        }
        // 指向新链表的起始位置
        ListNode newHead = iter.next;
        // 原始环路链表的最后一个节点进行截断
        iter.next = null;
        return newHead;
    }

    /**
     * 给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
     *
     */
    @Test
    public void partition() {
        ListNode head = ListNode.createList(1, 4, 3, 2, 5, 2);
        ListNode newList = partition(head, 3);
        newList.print();
    }

    private ListNode partition(ListNode head, int x) {
        ListNode small = new ListNode(-1); // 拼接链表中小于x的值
        ListNode smallHead = small;  // 一直指向small的头部
        ListNode large = new ListNode(-1); // 拼接链表中大于x的值
        ListNode largeHead = large; // 一直指向large的头部
        while(head != null){
            if (head.val < x) {
                small.next = head;
                small = small.next;
            } else {
                large.next = head;
                large = large.next;
            }
            head = head.next;
        }
        large.next = null; // 阶段原始链表的拼接
        small.next = largeHead.next; // 把small的尾部拼接上large的头部
        return smallHead.next; // 返回small的头部下一个值，就是真正的链表值
    }



}
