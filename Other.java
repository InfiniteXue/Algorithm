package algorithm;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

class Other {

    /**
     * #54: 给定一个m*n矩阵，按照顺时针螺旋顺序返回矩阵中的所有元素
     * 输入: [
     *         [ 1,  2,  3,  4, 5],
     *         [14, 15, 16, 17, 6],
     *         [13, 20, 19, 18, 7],
     *         [12, 11, 10,  9, 8]
     *      ]
     * 输出: [1,2,3,4,5,6,7,8,9,10,11,12]
     * https://leetcode.com/problems/spiral-matrix
     */
    public static List<Integer> spiralMatrix(int[][] matrix) {
        /*
         * int[][]即int[]中存储的是一个int[]的地址
         * 对应到矩阵中，第一维代表行索引，第二维实际存储每行的元素
         */
        List<Integer> result = new LinkedList<>();
        if (matrix.length == 0) {
            return result;
        }
        int rowStart = 0, rowEnd = matrix.length - 1;
        int columnStart = 0, columnEnd = matrix[0].length - 1;
        while (true) {
            // 上（1,2,3,4,5;15,16,17）
            for (int column = columnStart; column <= columnEnd; column++) {
                result.add(matrix[rowStart][column]);
            }
            if (++rowStart > rowEnd) {
                break;
            }
            // 右（6,7,8;18）
            for (int row = rowStart; row <= rowEnd; row++) {
                result.add(matrix[row][columnEnd]);
            }
            if (--columnEnd < columnStart) {
                break;
            }
            // 下（9,10,11,12;19,20）
            for (int column = columnEnd; column >= columnStart; column--) {
                result.add(matrix[rowEnd][column]);
            }
            if (--rowEnd < rowStart) {
                break;
            }
            // 左（13,14）
            for (int row = rowEnd; row >= rowStart; row--) {
                result.add(matrix[row][columnStart]);
            }
            if (++columnStart > columnEnd) {
                break;
            }
        }
        return result;
    }

    /**
     * #146: LRU--Least Recently Used
     * #460: LFU--Least Frequently Used
     * get(key): 若key存在则返回缓存值，否则返回-1
     * put(key, value): 当缓存容量达到上限时，LRU会删除最近最少使用的数据，LFU会删除最不经常使用的数据(若使用频率相同时则再遵循LRU)
     *      Cache cache = new Cache(3);
     *      cache.put(1,1);  cache.put(2,2);  cache.put(3,3);  cache.put(4,4);  // 删除1
     *      cache.get(1);  // 返回-1
     *      cache.get(2);  cache.get(2);  cache.get(4);  cache.get(3);
     *      cache.put(5,5);  // LRU会删除2，LFU会删除4
     * https://leetcode.com/problems/lru-cache
     * https://leetcode.com/problems/lfu-cache
     */
    public static class LRUCache {
        @Setter
        @Getter
        class DListNode {
            private int key;
            private int val;
            private DListNode previous;
            private DListNode next;
        }

        // 缓存容量
        private int capacity;
        private Map<Integer, DListNode> cache;
        // 双向链表的首和尾（用于定位首尾的空元素）
        private DListNode head = new DListNode(), tail = new DListNode();
        public LRUCache(int capacity) {
            this.capacity = capacity;
            cache = new HashMap<>((int) (capacity / 0.75 + 1));
            // 初始化双向链表
            head.setNext(tail);
            tail.setPrevious(head);
        }
        private void addFirstNode(DListNode node) {
            node.setPrevious(head);
            node.setNext(head.getNext());
            head.getNext().setPrevious(node);
            head.setNext(node);
        }
        private void removeNode(DListNode node) {
            DListNode previous = node.getPrevious();
            DListNode next = node.getNext();
            previous.setNext(next);
            next.setPrevious(previous);
        }
        private void moveToFirst(DListNode node) {
            removeNode(node);
            addFirstNode(node);
        }
        private DListNode pollLastNode() {
            DListNode last = tail.getPrevious();
            removeNode(last);
            return last;
        }
        public int get(int key) {
            DListNode node = cache.get(key);
            if (node == null) {
                return -1;
            }
            moveToFirst(node);
            return node.getVal();
        }
        public void put(int key, int value) {
            DListNode node = cache.get(key);
            if (node == null) {
                DListNode newNode = new DListNode();
                newNode.setKey(key);
                newNode.setVal(value);
                cache.put(key, newNode);
                addFirstNode(newNode);
                if (cache.size() > capacity) {
                    DListNode last = pollLastNode();
                    cache.remove(last.getKey());
                }
            } else {
                node.setVal(value);
                moveToFirst(node);
            }
        }
    }
    /* 基于java.util.public class LinkedHashMap<K,V> extends HashMap<K,V> implements Map<K,V>实现 */
    public static class LRUCacheByLinkedHashMap {
        private int capacity;
        private Map<Integer, Integer> cache;
        public LRUCacheByLinkedHashMap(int capacity) {
            this.capacity = capacity;
            // LinkedHashMap<K,V>中维护了一个双向链表，从而实现具有顺序的HashMap，可根据写入(默认)或访问顺序排序
            cache = new LinkedHashMap((int) (capacity / 0.75 + 1), 0.75F, true) {
                // This method is invoked by put and putAll after inserting a new entry into the map.
                @Override
                protected boolean removeEldestEntry(Map.Entry eldest) {
                    return cache.size() > capacity;
                }
            };
        }
        public int get(int key) {
            Integer value = cache.get(key);
            return value == null ? -1 : value;
        }
        public void put(int key, int value) {
            cache.put(key, value);
        }
    }
    public static class LFUCache {
        private int capacity;
        // key-value
        private Map<Integer, Integer> cache;
        // key-使用次数
        private Map<Integer, Integer> frequency;
        // 使用次数-keys（LinkedHashSet<E>基于LinkedHashMap<K,V>实现，按写入顺序排序）
        private Map<Integer, LinkedHashSet<Integer>> queue = new HashMap<>();
        // 最少使用次数
        private int minCount;
        public LFUCache(int capacity) {
            this.capacity = capacity;
            cache = new HashMap<>((int) (capacity / 0.75 + 1));
            frequency = new HashMap<>((int) (capacity / 0.75 + 1));
            queue.put(1, new LinkedHashSet<>());
        }
        private void refresh(int key) {
            // 更新使用次数
            Integer oldCount = frequency.get(key);
            Integer newCount = oldCount + 1;
            frequency.put(key, newCount);
            // 更新频率队列和最少使用次数
            queue.get(oldCount).remove(key);
            if (oldCount == minCount && queue.get(oldCount).isEmpty()) {
                minCount++;
            }
            /*
             * default V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction)---当key不存在或对应的value为null时，执行mappingFunction
             * 等价于LinkedHashSet<Integer> set = queue.get(newCount);  if (set == null) { queue.put(newCount, new LinkedHashSet<>()); }
             */
            queue.computeIfAbsent(newCount, t -> queue.put(newCount, new LinkedHashSet<>()));
            queue.get(newCount).add(key);
        }
        public int get(int key) {
            Integer value = cache.get(key);
            if (value == null) {
                return -1;
            }
            refresh(key);
            return value;
        }
        public void put(int key, int value) {
            Integer v = cache.get(key);
            if (v != null) {
                cache.put(key, value);
                refresh(key);
                return;
            }
            cache.put(key, value);
            if (cache.size() > capacity) {
                // LinkedHashSet<E>按写入顺序排序，首元素即最先写入元素
                Integer expired = queue.get(minCount).iterator().next();
                cache.remove(expired);
                frequency.remove(expired);
                queue.get(minCount).remove(expired);
            }
            frequency.put(key, 1);
            queue.get(1).add(key);
            minCount = 1;
        }
    }

    /**
     * #: 一只青蛙一次可以跳1级或2级台阶，跳上一个n级台阶共有多少种跳法
     *      f(1) = 1
     *      f(2) = 2
     *      f(n) = f(n-1) + f(n-2), (n>=3)
     * 注: 假设有五级台阶，则可以从第三级跳2级台阶到第五级，也可以从第四级跳1级台阶到第五级
     *     若跳上三级台阶共有f(3)种跳法，跳上四级台阶共有f(4)种跳法，则跳上五级台阶共有f(3)+f(4)种跳法
     */
    public static int jumpByRecursive(int n, Map<Integer, Integer> cache) {
        /* 递归---时间复杂度O(n)，空间复杂度O(n) */
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        if (cache.containsKey(n)) {
            return cache.get(n);
        }
        int total = jumpByRecursive(n - 1, cache) + jumpByRecursive(n - 2, cache);
        cache.put(n, total);
        return total;
    }
    public static int jumpByDynamicProgramming(int n) {
        /* 动态规划---时间复杂度O(n)，空间复杂度O(1) */
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int n_1 = 1, n_2 = 2, result = 0;
        for (int i = 3; i <= n; i++) {
            result = n_1 + n_2;
            n_1 = n_2;
            n_2 = result;
        }
        return result;
    }

    /**
     * #: 获取斐波那契数列第n个数的值（若n过大则结果会溢出，可对100007取模）
     *      f(n) = f(n-1) + f(n-2), (n>=3, f(1) = f(2) = 1)
     */
    public static int fibonacci(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        int n_1 = 1, n_2 = 1, result = 0;
        for (int i = 3; i <= n; i++) {
            result = (n_1 + n_2) % 100007;
            n_1 = n_2;
            n_2 = result;
        }
        return result;
    }

    /**
     * #: 二分查找法（一种在有序数组中查找指定值的搜索算法）
     * 输入: nums = [1, 2, 6, 9, 12, 19, 26], search = 12
     * 输出: 4
     */
    public static int binarySearch(int[] nums, int search) {
        // 搜索范围边界
        int rightBorder = 0;
        int leftBorder = nums.length;
        int mid;
        while (rightBorder <= leftBorder) {
            /*
             * 获取搜索范围的中间索引
             * mid = (rightBorder + leftBorder) / 2;  // 当左右边界值过大时，可能发生整型溢出
             * mid = rightBorder + (leftBorder - rightBorder) / 2;  // 避免整型溢出，执行效率没有位移运算高
             */
            mid = rightBorder + ((leftBorder - rightBorder) >>> 2);
            if (nums[mid] < search) {
                rightBorder = mid + 1;
            } else if (nums[mid] > search) {
                leftBorder = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

}
