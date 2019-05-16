package algorithm;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/** 二叉树 */
@Setter
@Getter
@RequiredArgsConstructor
class TreeNode {
    @NonNull
    private int val;
    private TreeNode left;
    private TreeNode right;
}

class BinaryTreeRelated {

    /**
     * #100: 给定两个二叉树，判断其是否相同（树的结构以及节点上的值都相同）
     * 输入: t1 = 1, t2 = 1
     *           /        \
     *          2          2
     * 输出: false
     * https://leetcode.com/problems/same-tree
     */
    public static boolean sameTree(TreeNode t1, TreeNode t2) {
        if (t1 == null || t2 == null) {
            // null == null --> true
            return t1 == t2;
        }
        if (t1.getVal() == t2.getVal()) {
            return sameTree(t1.getLeft(), t2.getLeft()) && sameTree(t1.getRight(), t2.getRight());
        }
        return false;
    }

    /**
     * --•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--
     * 二叉树深度优先搜索(DFS,Depth First Search，如中/前/后序遍历、获取最大深度)---常基于递归或栈实现
     * 二叉树广度优先搜索(BFS,Breadth First Search，如层次遍历、获取最大宽度)---常基于队列实现
     * --•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--
     */

    /**
     * #144: 二叉树前序遍历
     * #94: 二叉树中序遍历
     * #145: 二叉树后序遍历
     * 输入: 1
     *      /\
     *     2  3
     *     \  /
     *     4 5
     * 输出: 前序遍历(DLR,根左右)--12435，中序遍历(LDR,左根右)--24153，后序遍历(LRD,左右根)--42531
     * https://leetcode.com/problems/binary-tree-preorder-traversal
     * https://leetcode.com/problems/binary-tree-inorder-traversal
     * https://leetcode.com/problems/binary-tree-postorder-traversal
     */
    /* 前序遍历(递归) */
    public static void preOederByRecursive(TreeNode tree, List<Integer> list) {
        if (tree != null) {
            list.add(tree.getVal());
            if (tree.getLeft() != null) {
                preOederByRecursive(tree.getLeft(), list);
            }
            if (tree.getRight() != null) {
                preOederByRecursive(tree.getRight(), list);
            }
        }
    }
    /* 前序遍历(栈) */
    public static List<Integer> preOrderByStack(TreeNode tree) {
        LinkedList<Integer> result = new LinkedList<>();
        if (tree != null) {
            LinkedList<TreeNode> stack = new LinkedList<>();
            stack.add(tree);
            while (!stack.isEmpty()) {
                TreeNode temp = stack.pollLast();
                result.add(temp.getVal());
                if (temp.getRight() != null) {
                    stack.add(temp.getRight());
                }
                if (temp.getLeft() != null) {
                    stack.add(temp.getLeft());
                }
            }
        }
        return result;
    }
    /* 中序遍历(递归) */
    public static void inOrderByRecursive(TreeNode tree, List<Integer> list) {
        if (tree != null) {
            if (tree.getLeft() != null) {
                inOrderByRecursive(tree.getLeft(), list);
            }
            list.add(tree.getVal());
            if (tree.getRight() != null) {
                inOrderByRecursive(tree.getRight(), list);
            }
        }
    }
    /* 中序遍历(栈) */
    public static List<Integer> inOrderByStack(TreeNode tree) {
        LinkedList<Integer> result = new LinkedList<>();
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode current = tree;
        while (current != null || !stack.isEmpty()) {
            // 深度遍历左子树
            while (current != null) {
                stack.add(current);
                current = current.getLeft();
            }
            current = stack.pollLast();
            result.add(current.getVal());
            current = current.getRight();
        }
        return result;
    }
    /* 后序遍历(递归) */
    public static void postOrderByRecursive(TreeNode tree, List<Integer> list) {
        if (tree != null) {
            if (tree.getLeft() != null) {
                postOrderByRecursive(tree.getLeft(), list);
            }
            if (tree.getRight() != null) {
                postOrderByRecursive(tree.getRight(), list);
            }
            list.add(tree.getVal());
        }
    }
    /* 后序遍历(栈) */
    public static List<Integer> postOrder(TreeNode tree) {
        LinkedList<Integer> result = new LinkedList<>();
        if (tree != null) {
            LinkedList<TreeNode> stack = new LinkedList<>();
            stack.add(tree);
            while (!stack.isEmpty()) {
                TreeNode temp = stack.pollLast();
                result.addFirst(temp.getVal());
                if (temp.getLeft() != null) {
                    stack.add(temp.getLeft());
                }
                if (temp.getRight() != null) {
                    stack.add(temp.getRight());
                }
            }
        }
        return result;
    }

    /**
     * #102: 二叉树自顶向下按层次遍历
     * #107: 二叉树自底向上按层次遍历
     * 输入: 3
     *      /\
     *     9 20
     *       /\
     *     15  7
     * 输出: 自顶向下--[3],[9,20],[15,7]，自底向上--[15,7],[9,20],[3]
     * https://leetcode.com/problems/binary-tree-level-order-traversal
     * https://leetcode.com/problems/binary-tree-level-order-traversal-ii
     */
    public static List<List<Integer>> levelOrderFromTop(TreeNode tree) {
        List<List<Integer>> result = new LinkedList<>();
        if (tree != null) {
            LinkedList<TreeNode> queue = new LinkedList<>();
            queue.offer(tree);
            // 每次循环即自左向右遍历一层
            while (!queue.isEmpty()) {
                // 每层节点个数
                int levelSize = queue.size();
                List<Integer> sub = new LinkedList<>();
                for (int i = 0; i < levelSize; i++) {
                    if (queue.peek().getLeft() != null) {
                        queue.offer(queue.peek().getLeft());
                    }
                    if (queue.peek().getRight() != null) {
                        queue.offer(queue.peek().getRight());
                    }
                    sub.add(queue.poll().getVal());
                }
                result.add(sub);
            }
        }
        return result;
    }
    public static List<List<Integer>> levelOrderFromBottom(TreeNode tree) {
        /* 采用同levelOrderFromTop()一样的广度优先搜索，只是每层遍历结果的存储顺序相反 */
        return null;
    }

    /**
     * #103: 二叉树之字形层次遍历
     * 输入: 3
     *      /\
     *     9 20
     *       /\
     *     15  7
     * 输出: [3],[20,9],[15,7]
     * https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal
     */
    public static List<List<Integer>> zigzagLevelOrder(TreeNode tree) {
        List<List<Integer>> result = new ArrayList<>();
        if (tree != null) {
            // 奇层节点
            LinkedList<TreeNode> oddStack = new LinkedList<>();
            // 偶层节点
            LinkedList<TreeNode> evenStack = new LinkedList<>();
            oddStack.add(tree);
            while (!oddStack.isEmpty() || !evenStack.isEmpty()) {
                List<Integer> temp = new ArrayList<>();
                // 基于广度优先搜索和栈实现反向输出
                while (!oddStack.isEmpty()) {
                    TreeNode current = oddStack.pollLast();
                    temp.add(current.getVal());
                    if (current.getLeft() != null) {
                        evenStack.add(current.getLeft());
                    }
                    if (current.getRight() != null) {
                        evenStack.add(current.getRight());
                    }
                }
                result.add(temp);
                temp = new ArrayList<>();
                while (!evenStack.isEmpty()) {
                    TreeNode current = evenStack.pollLast();
                    temp.add(current.getVal());
                    if (current.getRight() != null) {
                        oddStack.add(current.getRight());
                    }
                    if (current.getLeft() != null) {
                        oddStack.add(current.getLeft());
                    }
                }
                if (!temp.isEmpty()) {
                    result.add(temp);
                }
            }
        }
        return result;
    }

    /**
     * --•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--
     * 满二叉树---除叶子节点外的所有节点都有两个子节点
     *      1.一个n层的满二叉树共有2^n-1个节点（满二叉树的节点总数一定是奇数）
     *      2.第i层共有2^(i-1)个节点（即共有2^(n-1)个叶子节点）
     * 完全二叉树---由满二叉树引出（若一个完全二叉树深度为k且有n个节点，则其每个节点都与深度为k的满二叉树前n个节点相对应）
     *      1.一个n层的完全二叉树，其叶子节点要么位于第n层，要么位于第n-1层
     *      2.若一个完全二叉树右子树的最大深度为l，则其左子树的最大深度为l或l+1
     * --•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--
     */

    /**
     * #: 给定一个二叉树，获取其节点总数
     * #222: 给定一个完全二叉树，获取其节点总数
     * 输入: 1
     *      /\
     *     2  3
     *    /\  /
     *   4 5 6
     * 输出: 6
     * https://leetcode.com/problems/count-complete-tree-nodes
     */
    public static int countBinaryTreeNodes(TreeNode tree) {
        /* 时间复杂度O(n) */
        if (tree == null) {
            return 0;
        }
        // 节点总数 = 根节点 + 左子树节点总数 + 右子树节点总数
        return 1 + countBinaryTreeNodes(tree.getLeft()) + countBinaryTreeNodes(tree.getRight());
    }
    public static int countCompleteTreeNodes(TreeNode tree) {
        /* 平均时间复杂度O(logn) */
        int mostLeftNodeHeight = mostLeftNodeHeight(tree);
        int mostRightNodeHeight = mostRightNodeHeight(tree);
        if (mostLeftNodeHeight == mostRightNodeHeight) {
            // 若完全二叉树的最左叶子节点高度和最右叶子节点高度相同，则该完全二叉树为满二叉树，节点总数为2^n-1
            return (int) Math.pow(2, mostLeftNodeHeight) - 1;
        }
        return 1 + countCompleteTreeNodes(tree.getLeft()) + countCompleteTreeNodes(tree.getRight());
    }
    /* 最左叶子节点的高度 */
    private static int mostLeftNodeHeight(TreeNode tree) {
        if (tree == null) {
            return 0;
        }
        return 1 + mostLeftNodeHeight(tree.getLeft());
    }
    /* 最右叶子节点的高度 */
    private static int mostRightNodeHeight(TreeNode tree) {
        if (tree == null) {
            return 0;
        }
        return 1 + mostRightNodeHeight(tree.getRight());
    }

    /**
     * #104: 给定一个二叉树，获取其最大深度
     * #662: 给定一个二叉树，获取其最大宽度（每层宽度即非空最左和最右节点之间的长度[两端点间的空节点也计入长度]）
     * 输入: t = 3
     *          /\
     *         9 20
     *           /\
     *         15  7
     * 输出: 最大深度--3，最大宽度--2
     * https://leetcode.com/problems/maximum-depth-of-binary-tree
     * https://leetcode.com/problems/maximum-width-of-binary-tree
     */
    public static int maximumDepth(TreeNode tree) {
        if (tree == null) {
            return 0;
        } else {
            int left = maximumDepth(tree.getLeft());
            int right = maximumDepth(tree.getRight());
            return Math.max(left, right) + 1;
        }
    }
    public static int maximumWidth(TreeNode tree) {
        /*
         * --•--•--•--•--•--•--•--•--•--•--
         * 满二叉树/完全二叉树序号模型
         *    n层的满二叉树共有2^n-1个节点
         *    左子节点序号为其父节点序号*2
         *    右子节点序号为其父节点序号*2+1
         * --•--•--•--•--•--•--•--•--•--•--
         */
        class TreeNodeWithIndex extends TreeNode {
            private int index;
            public TreeNodeWithIndex(TreeNode tree, int index) {
                super(tree.getVal());
                setLeft(tree.getLeft());
                setRight(tree.getRight());
                this.index = index;
            }
            public int getIndex() {
                return index;
            }
        }

        if (tree == null) {
            return 0;
        } else {
            int maximumWidth = 0;
            LinkedList<TreeNodeWithIndex> queue = new LinkedList<>();
            queue.offer(new TreeNodeWithIndex(tree, 1));
            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                // 当前层非空最左节点序号
                int left = queue.peek().getIndex();
                int right = left;
                for (int i = 0; i < levelSize; i++) {
                    // 自左向右遍历，最终为当前层非空最右节点序号
                    right = queue.peek().getIndex();
                    if (queue.peek().getLeft() != null) {
                        queue.offer(new TreeNodeWithIndex(queue.peek().getLeft(), queue.peek().getIndex() * 2));
                    }
                    if (queue.peek().getRight() != null) {
                        queue.offer(new TreeNodeWithIndex(queue.peek().getRight(), queue.peek().getIndex() * 2 + 1));
                    }
                    queue.poll();
                }
                maximumWidth = Math.max(maximumWidth, right - left + 1);
            }
            return maximumWidth;
        }
    }

    /**
     * --•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--
     * 二叉查找树又名二叉搜索树，其或是一棵空树或具有以下特点
     *      1.若其左子树不为空，则左子树上所有的节点均小于根节点
     *      2.若其右子树不为空，则右子树上所有的节点均大于根节点
     *      3.其左、右子树也分别为二叉查找树
     *      4.不存在相同节点
     * --•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--
     */

    /**
     * #235: 给定一个二叉查找树，获取该树中两个指定节点的最近公共祖先
     * 输入: t = 6, p = 8, q = 9
     *          /\
     *         2  8
     *        /\  /\
     *       0 4 7 9
     *        /\
     *       3 5
     * 输出: 8
     * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree
     */
    public static TreeNode lcaOfABinarySearchTree(TreeNode tree, TreeNode p, TreeNode q) {
        if (tree.getVal() > p.getVal() && tree.getVal() > q.getVal()) {
            // p和q都小于当前根节点，则最近公共祖先位于根节点的左子树上
            return lcaOfABinarySearchTree(tree.getLeft(), p, q);
        } else if (tree.getVal() < p.getVal() && tree.getVal() < q.getVal()) {
            // p和q都大于当前根节点，则最近公共祖先位于根节点的右子树上
            return lcaOfABinarySearchTree(tree.getRight(), p, q);
        } else {
            // p和q不都大于或小于根节点，即p和q位于当前根节点的两侧，则当前根节点即为最近公共祖先
            return tree;
        }
    }

    /**
     * #236: 给定一个二叉树，获取该树中两个指定节点的最近公共祖先
     * 输入: t = 3, p = 5, q = 4
     *          /\
     *         5  1
     *        /\  /\
     *       6 2 0 8
     *        /\
     *       7 4
     * 输出: 5
     * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree
     */
    public static TreeNode lcaOfABinaryTree(TreeNode tree, TreeNode p, TreeNode q) {
        /*
         * 深度优先搜索(递归)时，有三种情况
         *    1.p和q分别位于当前根节点的左右子树上(left和right都不为null)，则当前根节点即为最近公共祖先
         *    2.p和q都位于当前根节点的左子树上(left不为null，right为null)，则p和q最先搜索到的节点即为最近公共祖先
         *    3.p和q都位于当前根节点的右子树上(left为null，right不为null)，同2
         * 注: left和right都为null时即为叶子节点
         */
        if (tree == null || tree == p || tree == q) {
            return tree;
        }
        TreeNode left = lcaOfABinaryTree(tree.getLeft(), p, q);
        TreeNode right = lcaOfABinaryTree(tree.getRight(), p, q);
        if (left != null && right != null) {
            return tree;
        }
        return left == null ? right : left;
        // 等价于return left == null ? right : right == null ? left : tree;
    }

    /**
     * #98: 给定一个二叉树，判断其是否是二叉查找树
     * 输入: 2
     *      /\
     *     1  3
     * 输出: true
     * https://leetcode.com/problems/validate-binary-search-tree
     */
    public static boolean validateBinarySearchTree(TreeNode tree) {
        if (tree == null) {
            return true;
        }
        /* 中序遍历(栈)二叉查找树时，将得到一个递增序列 */
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode current = tree;
        TreeNode pre = null;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.add(current);
                current = current.getLeft();
            }
            current = stack.pollLast();
            // 要么pre为左子节点/current为根节点，要么pre为根节点/current为右子节点，左子节点<根节点<右子节点
            if (pre != null && pre.getVal() > current.getVal()) {
                return false;
            }
            pre = current;
            current = current.getRight();
        }
        return true;
    }

}
