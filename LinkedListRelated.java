package algorithm;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/** 单向链表 */
@Setter
@Getter
@RequiredArgsConstructor
class ListNode {
    @NonNull
    private int val;
    private ListNode next;
    // @RequiredArgsConstructor---默认生成public访问修饰符的有参构造器，参数包含类中所有@NonNull注释的非静态成员变量以及未初始化的final非静态成员变量
}

/** 双向链表 */
@Setter
@Getter
class DListNode {
    private int val;
    private DListNode previous;
    private DListNode next;
}

class LinkedListRelated {

    /**
     * #21: 将两个升序链表合并成一个新的升序链表
     * 输入: l1 = 1->2->4, l2 = 1->3->4
     * 输出: 1->1->2->3->4->4
     * https://leetcode.com/problems/merge-two-sorted-lists
     */
    public static ListNode mergeTwoSortedLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        if (l1.getVal() < l2.getVal()) {
            l1.setNext(mergeTwoSortedLists(l1.getNext(), l2));
            return l1;
        } else {
            l2.setNext(mergeTwoSortedLists(l1, l2.getNext()));
            return l2;
        }
    }

    /**
     * #206: 反转一个单向链表
     * 输入: 1->2->3->4->5
     * 输出: 5->4->3->2->1
     * 时间复杂度: O(n)，空间复杂度: O(1)
     * https://leetcode.com/problems/reverse-linked-list
     */
    public static ListNode reverseLinkedList(ListNode list) {
        // 上一节点
        ListNode previous = null;
        // 当前节点
        ListNode current = list;
        while (current != null) {
            ListNode nextTemp = current.getNext();
            current.setNext(previous);
            previous = current;
            current = nextTemp;
        }
        return previous;
    }

    /**
     * #19: 给定一个单向链表，删除其倒数第n个节点
     * 输入: list = 1->2->3->4->5, n = 2
     * 输出: 1->2->3->5
     * 时间复杂度: O(n)，空间复杂度O(1)
     * https://leetcode.com/problems/remove-nth-node-from-end-of-list
     */
    public static ListNode removeFromEndOfList(ListNode list, int n) {
        /* 双指针法：第一个指针领先第二个指针n个节点 */
        ListNode first = list;
        ListNode second = list;
        // 第一个指针先移动到第n个节点
        for (int i = 1; i <= n; i++) {
            first = first.getNext();
        }
        // 已移动到最后一个节点后面(即null)则删除首节点
        if (first == null) {
            return list.getNext();
        }
        // 若是获取其倒数第n个节点则无须再额外移动第一个指针
        first = first.getNext();
        while (first != null) {
            // 将第一个指针移动到链表的最后一个节点后面(即null)
            first = first.getNext();
            // 同时移动第二个指针，最终将停在倒数第n+1个节点
            second = second.getNext();
        }
        // 删除倒数第n个节点
        second.setNext(second.getNext().getNext());
        return list;
    }

}
