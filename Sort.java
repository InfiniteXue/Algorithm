package algorithm;

import java.util.Arrays;

public class Sort {

    /**
     * 快速排序---平均时间复杂度O(nlogn)/最坏时间复杂度O(n^2)，空间复杂度O(logn)，不稳定
     *     1.从待排序序列中任取一个元素(如第一个元素，该元素的选取决定了时间复杂度)作为分界线
     *     2.将比其小的所有元素放在左边，比其大的所有元素放在右边
     *     3.按此原则分别递归左右子序列，直至子序列中只剩一个元素为止
     */
    public static void quickSort(int[] arr, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }
        // 确定基准元素的位置
        int pivotIndex = pivotIndexByPadding(arr, startIndex, endIndex);
        // int pivotIndex = pivotIndexByChange(arr, startIndex, endIndex);
        System.out.println(Arrays.toString(arr));
        // 遍历左子序列（小于基准元素的序列）
        quickSort(arr, startIndex, pivotIndex - 1);
        // 遍历右子序列（大于基准元素的序列）
        quickSort(arr, pivotIndex + 1, endIndex);
    }
    /* 填充法 */
    private static int pivotIndexByPadding(int[] arr, int startIndex, int endIndex) {
        // 取第一个元素作为基准元素
        int pivot = arr[startIndex];
        // 填充指针（初始为基准元素索引）
        int paddingIndex = startIndex;
        // 左指针
        int leftIndex = startIndex;
        // 右指针
        int rightIndex = endIndex;
        // 所有元素只遍历一遍
        while (leftIndex < rightIndex) {
            // 若右指针所指向的元素小于基准元素则填充至paddingIndex，并将paddingIndex指向当前位置，左指针向右移动，跳出循环；否则将右指针向左移动
            while (leftIndex < rightIndex) {
                if (arr[rightIndex] < pivot) {
                    arr[paddingIndex] = arr[rightIndex];
                    paddingIndex = rightIndex;
                    leftIndex++;
                    break;
                }
                rightIndex--;
            }
            // 若左指针所指向的元素大于基准元素则填充至paddingIndex，并将paddingIndex指向当前位置，右指针向左移动，跳出循环；否则将左指针向右移动
            while (leftIndex < rightIndex) {
                if (arr[leftIndex] > pivot) {
                    arr[paddingIndex] = arr[leftIndex];
                    paddingIndex = leftIndex;
                    rightIndex--;
                    break;
                }
                leftIndex++;
            }
        }
        // 将基准元素填充至paddingIndex
        arr[paddingIndex] = pivot;
        return paddingIndex;
    }
    /* 交换法 */
    private static int pivotIndexByChange(int[] arr, int startIndex, int endIndex) {
        int pivot = arr[startIndex];
        int leftIndex = startIndex;
        int rightIndex = endIndex;
        while (leftIndex < rightIndex) {
            // 若右指针所指向的元素大于基准元素则向左移动（直至移到第一个小于基准元素或leftIndex）
            while (leftIndex < rightIndex && arr[rightIndex] > pivot) {
                rightIndex--;
            }
            // 若左指针所指向的元素小于等于基准元素则向右移动（直至移到第一个大于基准元素或rightIndex）
            while (leftIndex < rightIndex && arr[leftIndex] <= pivot) {
                leftIndex++;
            }
            if (leftIndex < rightIndex) {
                // 交换左右指针所指向的元素
                int temp = arr[leftIndex];
                arr[leftIndex] = arr[rightIndex];
                arr[rightIndex] = temp;
            }
        }
        // 此时leftIndex和rightIndex重合
        int temp = arr[leftIndex];
        arr[leftIndex] = arr[startIndex];
        arr[startIndex] = temp;
        return leftIndex;
    }

    /**
     * --•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--
     * 二叉堆本质上是一种完全二叉树，分为：
     *     小顶堆---任何一个父节点的值都小于等于其孩子节点的值，堆顶为最小值
     *     大顶堆---任何一个父节点的值都大于等于其孩子节点的值，堆顶为最大值
     * --•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--•--
     */

    /**
     * 堆排序---时间复杂度O(nlogn)，空间复杂度O(1)，不稳定
     *     1.将待排序序列调整为二叉堆（升序使用大顶堆，降序使用小顶堆）
     *     2.循环将堆顶与最后一个非有序节点互换，并重新调整堆顶
     */
    public static void heapSort(int[] arr) {
        // 调整为大顶堆（从最后一个非叶子节点开始调整）
        for (int i = (arr.length - 2) / 2; i >= 0; i--) {
            adjust(arr, i, arr.length);
        }
        System.out.println(Arrays.toString(arr));
        for (int i = arr.length - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            // 调整堆顶
            adjust(arr, 0, i);
        }
        System.out.println(Arrays.toString(arr));
    }
    /*
     * 大顶堆调整（若父节点小于孩子节点则下沉）
     * @param arr           待调整的堆
     * @param parentIndex   需要调整的父节点
     * @param length        待调整的堆大小
     */
    private static void adjust(int[] arr, int parentIndex, int length) {
        int temp = arr[parentIndex];
        int childIndex = parentIndex * 2 + 1;
        while (childIndex < length) {
            // 定位到最大的孩子节点
            if (childIndex + 1 < length && arr[childIndex + 1] > arr[childIndex]) {
                childIndex++;
            }
            // 若父节点不小于任一孩子节点，则无需调整
            if (temp >= arr[childIndex]) {
                break;
            }
            // 否则将父节点下沉，最大的孩子节点上浮（最直接的处理即交换父节点与最大孩子节点，但因为temp还需要继续与下一级孩子节点比较，所以可先不交换仅单向赋值进行优化）
            arr[parentIndex] = arr[childIndex];
            parentIndex = childIndex;
            childIndex = parentIndex * 2 + 1;
        }
        arr[parentIndex] = temp;
    }

}
