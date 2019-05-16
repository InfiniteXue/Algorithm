package algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

class StringRelated {

    /**
     * #14: 找出给定字符串数组中的最长公共前缀（字符串数组只包含小写字母a-z）
     * 输入: ["flower","flow","flight"]
     * 输出: "fl"
     * https://leetcode.com/problems/longest-common-prefix
     */
    public static String longestCommonPrefix(String[] strs) {
        /* 二分查找法 */
        if (strs == null || strs.length == 0) {
            return "";
        }
        // strs中最短字符串的长度
        int minLen = Integer.MAX_VALUE;
        for (String str : strs) {
            minLen = Math.min(str.length(), minLen);
        }
        int start = 1;
        int end = minLen;
        while (start <= end) {
            int middle = (start + end) / 2;
            if (isCommonPrefix(strs, middle)) {
                start = middle + 1;
            } else {
                end = middle - 1;
            }
        }
        return strs[0].substring(0, (start + end) / 2);
    }
    /* 判断给定字符串数组中各字符串的前len个子字符串是否为公共前缀 */
    private static boolean isCommonPrefix(String[] strs, int len) {
        // 提取前len个子字符串
        String prefix = strs[0].substring(0, len);
        for (int i = 0; i < strs.length; i++) {
            if (!strs[i].startsWith(prefix)) {
                return false;
            }
        }
        return true;
    }

    /**
     * #20: 给定一个只包含'('、')'、'{'、'}'、'['、']'的字符串，判断字符串是否以正确的顺序闭合
     * 输入: {[]}
     * 输出: true
     * 时间复杂度: O(n)，空间复杂度: O(n)
     * https://leetcode.com/problems/valid-parentheses
     */
    public static boolean validParentheses(String str) {
        Map<Character, Character> parentheses = new HashMap<>();
        parentheses.put(')', '(');
        parentheses.put('}', '{');
        parentheses.put(']', '[');
        // 栈（只有左括号才会入栈）
        LinkedList<Character> stack = new LinkedList<>();
        for (int i = 0; i < str.length(); i++) {
            Character character = str.charAt(i);
            if (parentheses.containsKey(character)) {
                // 当前为右括号，出栈获取左括号并判断是否匹配
                Character ch = stack.pollLast();
                if (ch == null || !ch.equals(parentheses.get(character))) {
                    return false;
                }
            } else {
                // 当前为左括号，入栈
                stack.add(character);
            }
        }
        // 若以正确的顺序闭合则栈最终为空
        return stack.isEmpty();
    }

    /**
     * #125: 给定一个字符串，验证其是否是回文串（只考虑字母和数字且忽略字母大小写）
     * 输入: A man, a plan, a canal. Panama.
     * 输出: true
     * https://leetcode.com/problems/valid-palindrome
     */
    public static boolean isPalindrome(String str) {
        if (str == null || str.length() == 0) {
            // 空字符串
            return true;
        }
        /* 双指针法: 左右指针依次移动并进行对比（跳过非字母或数字） */
        int leftHand = 0;
        int rightHand = str.length() - 1;
        while (leftHand < rightHand) {
            if (!Character.isLetterOrDigit(str.charAt(leftHand))) {
                // 非字母或数字，左指针右移
                leftHand++;
            } else if (!Character.isLetterOrDigit(str.charAt(rightHand))) {
                // 非字母或数字，右指针左移
                rightHand--;
            } else {
                if (Character.toLowerCase(str.charAt(leftHand)) != Character.toLowerCase(str.charAt(rightHand))) {
                    return false;
                }
                leftHand++;
                rightHand--;
            }
        }
        return true;
    }

}
